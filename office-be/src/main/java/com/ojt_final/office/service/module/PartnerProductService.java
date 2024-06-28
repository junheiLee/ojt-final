package com.ojt_final.office.service.module;

import com.ojt_final.office.dao.PartnerProductDao;
import com.ojt_final.office.domain.PartnerProduct;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchProcessor;
import com.ojt_final.office.service.batch.BatchResult;
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
public class PartnerProductService extends ExcelHandler {

    private final BatchProcessor batchProcessor;
    private final PartnerProductDao partnerProductDao;

    @Override
    public UploadExcelResponse saveExcelData(MultipartFile excelFile) throws IOException {

        validExtension(excelFile); // 파일이 Excel 확장자(.xlsx, .xls)인지 확인
        List<PartnerProduct> partnerProducts
                = excelConverter.parse(excelFile.getInputStream(), PartnerProduct.class);

        int previousCount = partnerProductDao.countAll();
        BatchResult batchResult
                = batchProcessor.save(BATCH_SIZE, partnerProducts, partnerProductDao::saveAll)
                .calInsertAndMaintainThenSet(previousCount, partnerProductDao.countAll());

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD_RESULT)
                .batchResult(batchResult)
                .build();
    }

    /**
     * 파트너 상품 코드에 해당하는 모든 링크 여부 변경
     * TODO: 배치 필요 여부 고민하기
     *
     * @param isLinked 연결 여부
     * @param codes    대상 파트너 상품 코드 리스트
     * @return 변경된 데이터 수
     */
    public int updateAllIsLinked(boolean isLinked, List<String> codes) {

        return partnerProductDao.updateAllIsLinked(isLinked, codes);
    }

}
