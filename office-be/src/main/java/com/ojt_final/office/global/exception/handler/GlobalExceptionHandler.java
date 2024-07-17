package com.ojt_final.office.global.exception.handler;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ojt_final.office.dto.response.BaseResponse;
import com.ojt_final.office.dto.response.ErrorResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.DuplicateIdentifierException;
import com.ojt_final.office.global.exception.excel.ExcelInternalException;
import com.ojt_final.office.global.exception.excel.NoExcelColumnAnnotationsException;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.util.RecordFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

import static com.ojt_final.office.dto.response.constant.ResultCode.*;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {

        log.error("[BAD REQUEST]: 존재하지 않는 URL 요청");
        return buildErrorResponse(ResultCode.NOT_EXIST_URL, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorCode = error.getDefaultMessage();
            errors.put(fieldName, errorCode);
        });

        log.error("[BAD REQUEST]: 유효성 검사 실패 항목={}", errors);
        return buildErrorResponse(ResultCode.INVALID, errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getName(), ex.getValue() + "->" + ex.getRequiredType().getSimpleName());

        log.error("[BAD REQUEST]: 타입 불일치 항목={}", errors);
        return buildErrorResponse(TYPE_MISMATCH, errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        Throwable mostSpecificCause = ex.getMostSpecificCause();

        if (mostSpecificCause instanceof InvalidFormatException ifex) {

            Map<String, String> errors = new HashMap<>();
            String fieldName = ifex.getPath().get(0).getFieldName();
            errors.put(fieldName, ifex.getValue() + "->" + ifex.getTargetType().getSimpleName());

            log.error("[BAD REQUEST]: 타입 불일치 항목={}", errors);
            return buildErrorResponse(TYPE_MISMATCH, errors, HttpStatus.BAD_REQUEST);

        } else {

            log.error("[BAD REQUEST]: 올바르지 않은 Body 요청");
            return buildErrorResponse(EMPTY_BODY, HttpStatus.BAD_REQUEST);
        }

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        log.error("[ERROR]: ", ex);
        return buildErrorResponse(FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public BaseResponse handleMultipartException(MultipartException e) {
        log.error("[MultipartException]: {}", e.getMessage());
        return new BaseResponse(ResultCode.NO_FILE);
    }

    @ExceptionHandler(DuplicateIdentifierException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateIdentifierException(DuplicateIdentifierException ex) {
        log.error("[BAD REQUEST]: 식별자 중복", ex.getCause());
        return buildErrorResponse(DUPLICATE_IDENTIFIER, HttpStatus.CONFLICT);
    }

    // [Excel 관련 예외 처리]
    @ExceptionHandler(POIXMLException.class)
    public ResponseEntity<ErrorResponse> handlePOIXMLException(POIXMLException e) {
        log.error("[ExcelException]: {}", e.getMessage());

        if (e.getMessage().contains("#57699")) { // 스프레드 시트 업로드 불가
            return buildErrorResponse(ResultCode.UNSUPPORTED_EXCEL_FORMAT, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        return buildErrorResponse(ResultCode.CORRUPTED_OR_INVALID_EXCEL_FILE, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(UnSupportedFileException.class)
    public BaseResponse handleUnSupportedFileException(UnSupportedFileException e) {

        log.error("[ExcelException] excel 외 파일 업로드 시도");
        return new BaseResponse(e.getCode());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NoExcelColumnAnnotationsException.class)
    public BaseResponse handleNoExcelColumnAnnotationsException(NoExcelColumnAnnotationsException e) {

        log.error("[ExcelException] excel 파일 헤더와 업로드하고자 하는 도메인이 다름");
        return new BaseResponse(e.getCode());
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(RecordFormatException.class)
    public BaseResponse handleRecordFormatException() {

        log.error("[ExcelException] SIZE OVER");
        return new BaseResponse(ResultCode.TOO_BIG_SIZE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExcelInternalException.class)
    public void handleExcelInternalException(ExcelInternalException e) {

        log.error("[ExcelException] :", e);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ResultCode resultCode, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(resultCode), status);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ResultCode resultCode,
                                                             Map<String, String> errors,
                                                             HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(resultCode, errors), status);
    }
}
