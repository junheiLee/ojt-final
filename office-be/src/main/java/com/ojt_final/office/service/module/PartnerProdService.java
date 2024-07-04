package com.ojt_final.office.service.module;

import com.ojt_final.office.dao.PartnerProdDao;
import com.ojt_final.office.domain.PartnerProd;
import com.ojt_final.office.domain.search.PartnerProdCond;
import com.ojt_final.office.dto.request.search.CondParam;
import com.ojt_final.office.dto.response.PartnerProdListResponse;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.batch.BatchProcessor;
import com.ojt_final.office.service.batch.BatchResult;
import com.ojt_final.office.service.excel.AbstractExcelService;
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
public class PartnerProdService extends AbstractExcelService<PartnerProd> {

    private final BatchProcessor batchProcessor;
    private final PartnerProdDao partnerProdDao;

    @Override
    public Class<PartnerProd> getTargetDomain() {
        return PartnerProd.class;
    }

    public UploadExcelResponse saveExcelData(MultipartFile excelFile) throws IOException {

        List<PartnerProd> partnerProds = parse(excelFile);
        BatchResult batchResult = saveAll(partnerProds);

        return UploadExcelResponse.builder()
                .code(ResultCode.UPLOAD_RESULT)
                .batchResult(batchResult)
                .build();
    }

    private BatchResult saveAll(List<PartnerProd> partnerProds) {

        int previousCount = partnerProdDao.countAll();   // 생성된 데이터 수를 구하기 위한 이전 데이터 수
        return batchProcessor.save(BATCH_SIZE, partnerProds, partnerProdDao::saveAll)
                .calInsertAndMaintainThenSet(previousCount, partnerProdDao.countAll());
    }

    public byte[] createExcelFile(CondParam condParam) {

        PartnerProdCond cond = condParam.toPartnerProdCond();
        List<PartnerProd> prods = getProds(cond);

        return create(prods);
    }

    public PartnerProdListResponse getResponseProds(CondParam condParam) {

        PartnerProdCond cond = condParam.toPartnerProdCond();
        int count = partnerProdDao.countByCond(cond);
        List<PartnerProd> prods = getProds(cond);

        return PartnerProdListResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .totalItemsCount(count)
                .prods(prods)
                .build();
    }

    private List<PartnerProd> getProds(PartnerProdCond cond) {

        return partnerProdDao.selectByCond(cond);
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

        return partnerProdDao.updateAllIsLinked(isLinked, codes);
    }

}
