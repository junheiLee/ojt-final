package com.ojt_final.office.service.module;

import com.ojt_final.office.dao.CategoryDao;
import com.ojt_final.office.domain.Category;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchProcessor;
import com.ojt_final.office.service.batch.BatchResult;
import com.ojt_final.office.service.excel.ExcelProcessingHandler;
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

    public UploadExcelResponse saveExcelData(MultipartFile file) throws IOException {

        List<Category> categories = parse(file);
        BatchResult batchResult = saveAll(categories);

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD_RESULT)
                .batchResult(batchResult)
                .build();
    }

    private BatchResult saveAll(List<Category> categories) {

        int previousCount = categoryDao.countAll(); // 생성된 데이터 수를 구하기 위한 이전 데이터 수
        return batchProcessor.save(BATCH_SIZE, categories, categoryDao::saveAll)
                .calInsertAndUnchangedCount(previousCount, categoryDao.countAll());
    }
}
