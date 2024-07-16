package com.ojt_final.office.dto.response.constant;

import lombok.Getter;

@Getter
public enum ResultCode {

    // 성공
    SUCCESS("성공적으로 처리되었습니다."),
    CREATED("생성된 상품 코드: %d"),
    UPLOAD("생성: %d개, 수정: %d개, 유지: %d개, 실패: %d개"),

    // 서비스 계층
    NO_FILE("파일이 존재하지 않습니다.."),
    DUPLICATE_IDENTIFIER("이미 존재하는 상품입니다. 코드를 확인해 수정해주세요."),
    FAILED("임시"),

    // Excel
    NOT_EXCEL_FILE("Excel 파일이 아닙니다."),
    TOO_BIG_SIZE("파일의 용량이 너무 큽니다."),
    UNSUPPORTED_EXCEL_FORMAT("지원되지 않는 Excel 형식입니다. Excel 통합 문서로 저장해주세요."),
    CORRUPTED_OR_INVALID_EXCEL_FILE("파일이 손상되었거나 유효하지 않은 구조입니다."),
    MISSING_HEADERS("업로드하는 파일의 헤더를 확인해 주세요."),

    // Validation
    INVALID("다음 항목의 값을 확인해 주세요."),
    TYPE_MISMATCH("다음의 타입이 일치하지 않습니다."),
    EMPTY_BODY("요청 본문이 없거나 잘못된 형식입니다."),
    NOT_EXIST_URL("존재하지 않는 URL 입니다."),

    // 상태
    UNLINKED("링크 되지 않은 상품"),
    LINKED("링크된 상품");

    private final String message;

    ResultCode(String message) {
        this.message = message;
    }
}
