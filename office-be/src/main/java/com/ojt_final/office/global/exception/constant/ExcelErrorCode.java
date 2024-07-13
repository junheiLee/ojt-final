package com.ojt_final.office.global.exception.constant;

import lombok.Getter;

@Getter
public enum ExcelErrorCode {

    NOT_EXCEL_FILE("Excel 파일이 아닙니다."),
    TOO_BIG_SIZE("파일의 용량이 너무 큽니다."),
    UNSUPPORTED_EXCEL_FORMAT("지원되지 않는 Excel 형식입니다. 스프레드 시트가 아닌, Excel 통합 문서로 저장해주세요."),
    CORRUPTED_OR_INVALID_EXCEL_FILE("파일이 손상되었거나 유효하지 않은 구조입니다."),
    MISSING_HEADERS("업로드하는 파일의 헤더를 확인해 주세요.");

    private final String message;

    ExcelErrorCode(String message) {
        this.message = message;
    }

}
