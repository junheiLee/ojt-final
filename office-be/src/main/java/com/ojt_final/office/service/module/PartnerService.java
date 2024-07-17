package com.ojt_final.office.service.module;

import com.ojt_final.office.dao.PartnerDao;
import com.ojt_final.office.domain.Partner;
import com.ojt_final.office.dto.response.PartnerListResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PartnerService {

    private final PartnerDao partnerDao;

    /**
     * @return
     */
    public PartnerListResponse getList() {

        List<Partner> partners = partnerDao.selectAll();
        return PartnerListResponse
                .builder()
                .code(ResultCode.SUCCESS)
                .partners(partners)
                .build();
    }
}
