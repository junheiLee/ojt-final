package com.ojt_final.office.service;

import com.ojt_final.office.dao.CategoryDao;
import com.ojt_final.office.domain.Category;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import com.ojt_final.office.service.batch.BatchService;
import com.ojt_final.office.service.excel.ExcelConverter;
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

    private final ExcelConverter excelConverter;
    private final BatchService batchService;
    private final CategoryDao categoryDao;

    /**
     * 카테고리 정보가 담겨있는 엑셀 파일을 파싱해 DB에 저장하는 기능
     *
     * @param excelFile 카테고리 정보가 담겨있는 엑셀 파일, 헤더: [대분류코드, 대분류명]
     * @return 해당 정보 저장 성공 개수, 실패 개수 및 결과 코드를 담은 객체
     */
    public UploadExcelResponse<Object> saveExcelData(MultipartFile excelFile) throws IOException {

        // 파일이 Excel 확장자(.xlsx, .xls) 확인
        if (!excelConverter.supports(excelFile.getOriginalFilename())) {
            throw new UnSupportedFileException(ResultCode.NOT_EXCEL_FILE);
        }

        List<Category> categories = excelConverter.parseExcel(excelFile.getInputStream(), Category.class);

        int previousCount = categoryDao.countAll();
        int affectedRow = batchService.save(categories, categoryDao::saveAll, BATCH_SIZE);

        // 각각 생성, 수정, 유지 Row 개수를 계산해 Dto에 담아 반환
        return calUploadExcelResponse(categories.size(), previousCount, affectedRow);
    }

}
