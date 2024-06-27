package com.ojt_final.office.service;

import com.ojt_final.office.dao.CategoryDao;
import com.ojt_final.office.domain.Category;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchResult;
import com.ojt_final.office.service.batch.BatchService;
import com.ojt_final.office.service.excel.ExcelHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.ojt_final.office.global.constant.CommonConst.BATCH_SIZE;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService extends ExcelHandler {

    private final BatchService batchService;
    private final CategoryDao categoryDao;

    /**
     * 카테고리 정보가 담겨있는 엑셀 파일을 파싱해 DB에 저장하는 기능
     *
     * @param excelFile 카테고리 정보가 담겨있는 엑셀 파일, 헤더: [대분류코드, 대분류명]
     * @return 해당 정보 저장 성공 개수, 실패 개수 및 결과 코드를 담은 객체
     */
    @Override
    public UploadExcelResponse<Object> saveExcelData(MultipartFile excelFile) throws IOException {

        validExtension(excelFile); // 파일이 Excel 확장자(.xlsx, .xls)인지 확인
        List<Category> categories = excelConverter.parse(excelFile.getInputStream(), Category.class);

        int previousCount = categoryDao.countAll(); // 생성된 데이터 수를 구하기 위한 이전 데이터 수
        BatchResult batchResult
                = batchService.save(BATCH_SIZE, categories, categoryDao::saveAll)
                .calInsertAndMaintainThenSet(previousCount, categoryDao.countAll());

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD_RESULT)
                .batchResult(batchResult)
                .build();
    }

}
