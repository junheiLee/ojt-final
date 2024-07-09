package com.ojt_final.office.global.exception.handler;


import com.ojt_final.office.dto.response.BaseResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.ExcelInternalException;
import com.ojt_final.office.global.exception.excel.NoExcelColumnAnnotationsException;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.util.RecordFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.ojt_final.office.dto.response.constant.ResultCode.TOO_BIG_SIZE;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Spring 자체 예외, 통일된 포맷으로 변경
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatusCode status,
                                                                         WebRequest request) {
        log.error("[SPRING EXCEPTION]={}", ex.getMessage());
        return new ResponseEntity<>(new BaseResponse(ResultCode.SPRING_EXCEPTION), status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MultipartException.class)
    public BaseResponse excelHandle(MultipartException e) {
        log.error("[MultipartException]: {}", e.getMessage());
        return new BaseResponse(ResultCode.NO_FILE);
    }

    // [Excel 관련 예외 처리]
    @ExceptionHandler(POIXMLException.class)
    public ResponseEntity<Object> spreadSheetExHandle(POIXMLException e) {
        log.error("[POIXMLException]: {}", e.getMessage());

        if (e.getMessage().contains("#57699")) {
            return new ResponseEntity<>(
                    new BaseResponse(ResultCode.UNSUPPORTED_EXCEL_FORMAT),
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        return new ResponseEntity<>(
                new BaseResponse(ResultCode.CORRUPTED_OR_INVALID_EXCEL_FILE),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(UnSupportedFileException.class)
    public BaseResponse UnSupportedFileExHandle(UnSupportedFileException e) {

        log.error("[ExcelException] excel 외 파일 업로드 시도");
        return new BaseResponse(e.getResultCode());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NoExcelColumnAnnotationsException.class)
    public BaseResponse NoExcelColumnAnnotationsExHandle(NoExcelColumnAnnotationsException e) {

        log.error("[ExcelException] excel 파일 헤더와 업로드하고자 하는 도메인이 다름");
        return new BaseResponse(e.getResultCode());
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(RecordFormatException.class)
    public BaseResponse excelSizeExHandle() {
        log.error("[ExcelException] SIZE OVER");
        return new BaseResponse(TOO_BIG_SIZE);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExcelInternalException.class)
    public void ExcelInternalExHandle(ExcelInternalException e) {

        log.error("[ExcelException] 해당 없음", e);
    }
}
