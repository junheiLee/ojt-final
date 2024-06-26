package com.ojt_final.office.service.excel;

import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract class ExcelHandler {

    /**
     * 상품 정보가 담겨있는 엑셀 파일을 파싱해 DB에 저장하는 기능
     *
     * @param excelFile 상품 정보가 담겨있는 엑셀 파일
     * @return 해당 정보 기반 생성, 수정, 실패 개수 및 결과 코드를 담은 객체
     */
    protected abstract UploadExcelResponse<Object> saveExcelData(MultipartFile excelFile) throws IOException;


    /**
     * Excel Upload 후 결과값을 계산해 반환하는 기능
     *
     * @param targetCount   저장하려던 객체 리스트 원본 사이즈
     * @param previousCount 저장하고자 하는 테이블의 기존 Row 개수
     * @param affectedRow   Mysql이 반환한 숫자
     * @return 생성, 유지, 수정 개수를 기입한 객체
     */
    protected UploadExcelResponse<Object> calUploadExcelResponse(int targetCount, int previousCount, int affectedRow) {

        int insertCount = 0, updateCount = 0, maintainCount = 0, failedCount = 0;

        insertCount = targetCount - previousCount;
        updateCount = affectedRow - targetCount;
        maintainCount = affectedRow - (2 * updateCount) - insertCount;

        return UploadExcelResponse.builder()
                .insertCount(insertCount)
                .updateCount(updateCount)
                .maintainCount(maintainCount)
                .code(ResultCode.UPLOAD_RESULT)
                .build();
    }
}
