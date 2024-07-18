package com.ojt_final.office.category;

import com.ojt_final.office.category.dto.CategoriesResponse;
import com.ojt_final.office.global.batch.BatchProcessor;
import com.ojt_final.office.global.batch.BatchResult;
import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.excel.ExcelProcessingHandler;
import com.ojt_final.office.link.dto.UploadExcelResponse;
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
public class CategoryService extends ExcelProcessingHandler<Category> {

    private final BatchProcessor batchProcessor;
    private final CategoryDao categoryDao;

    @Override
    public Class<Category> getTargetDomain() {
        return Category.class;
    }

    /**
     * Excel 파일을 파싱해 Category 데이터를 저장한다.
     *
     * @param excelFile the Excel file to be parsed
     * @return response containing the result of the upload
     * @throws IOException if an I/O error occurs, particularly when checking the file extension with {@code file.getOriginalFilename()}
     */
    public UploadExcelResponse importExcel(MultipartFile excelFile) throws IOException {

        List<Category> categories = parse(excelFile);
        BatchResult batchResult = saveAll(categories);

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD)
                .batchResult(batchResult)
                .build();
    }

    /**
     * 모든 category 목록을 가져온다.
     *
     * @return response containing the list of categories and a success code
     */
    public CategoriesResponse getList() {

        List<Category> categories = categoryDao.selectAll();
        return CategoriesResponse
                .builder()
                .code(ResultCode.SUCCESS)
                .categories(categories)
                .build();
    }

    /**
     * Saves all Category entities in batch mode.
     *
     * @param categories the list of Category entities to be saved
     * @return result of the batch save operation
     */
    private BatchResult saveAll(List<Category> categories) {

        int previousCount = categoryDao.countAll(); // 생성된 데이터 수를 구하기 위한 이전 데이터 수
        return batchProcessor.save(BATCH_SIZE, categories, categoryDao::insertAll)
                .calInsertAndUnchangedCount(previousCount, categoryDao.countAll());
    }

}
