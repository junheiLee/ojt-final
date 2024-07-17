import axios from "axios";

const errorCodeMessages = {
    "NOT_EXCEL_FILE": "Excel 파일이 아닙니다.",
    "TOO_BIG_SIZE": "파일의 용량이 너무 큽니다.",
    "UNSUPPORTED_EXCEL_FORMAT": "지원되지 않는 Excel 형식입니다. Excel 통합 문서로 저장해주세요.",
    "CORRUPTED_OR_INVALID_EXCEL_FILE": "파일이 손상되었거나 유효하지 않은 구조입니다.",
    "MISSING_HEADERS": "업로드하는 파일의 헤더를 확인해 주세요.",
    "UPLOAD": "생성: %d개, 수정: %d개, 유지: %d개, 실패: %d개"
};

export const uploadFile = async (option, excelFile) => {

    try {
        const response = await axios.post(`/${option}/upload/excel`, excelFile, {
            headers: {
                "Content-type": "multipart/form-data"
            }
        });

    
        const result = response.data;
        let message = `생성: ${result.createdCount}개`
            + `\n수정: ${result.updatedCount}개`
            + `\n유지: ${result.unchangedCount}개`
            + `\n실패: ${result.failedCount}개`;
        
        console.log("여기까지는?");

        alert(message);

        if(option == "partner-products") {
            console.log("이프문 안");
            message = message + `\n기준 상품 변경: ${result.standardChangedCount}개`;
        }
        
        alert(message);

    } catch(error) {

        alert(error);

        console.log(error);
        const errorCode = error.response.data.code;
        const errorMessage = errorCodeMessages[errorCode];

        alert(errorMessage);

    }

}