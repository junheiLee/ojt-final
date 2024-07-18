package com.ojt_final.office.company;

import com.ojt_final.office.company.dto.PartnersResponse;
import com.ojt_final.office.global.constant.ResultCode;
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
    public PartnersResponse getList() {

        List<Partner> partners = partnerDao.selectAll();
        return PartnersResponse
                .builder()
                .code(ResultCode.SUCCESS)
                .partners(partners)
                .build();
    }
}
