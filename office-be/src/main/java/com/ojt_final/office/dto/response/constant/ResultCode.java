package com.ojt_final.office.dto.response.constant;

import lombok.Getter;

@Getter
public enum ResultCode {

    //성공
    SUCCESS("성공적으로 처리되었습니다."),
    UPLOAD_RESULT("생성: %d개, 수정: %d개, 유지: %d개"),

    // 실패: Excel 관련
    NOT_EXCEL_FILE("Excel 파일이 아닙니다."),
    TOO_BIG_SIZE("Excel 파일의 용량이 너무 큽니다."),
    NOT_INTEGRATED_EXCEL_FILE("스프레드 시트는 업로드할 수 없습니다. \nExcel 통합 문서로 저장해주세요."),
    NOT_HAVE_DOMAIN("업로드하는 파일의 옵션을 확인해 주세요. \n현재 옵션: "),

    // 예외: 공통
    NO_FILE("파일이 존재하지 않습니다.."),
    FAILED("임시"),
    SPRING_EXCEPTION("SPRING 예외 발생");

    private final String message;

    ResultCode(String message) {
        this.message = message;
    }
}
