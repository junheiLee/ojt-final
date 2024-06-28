package com.ojt_final.office.service;

import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.response.BaseResponse;
import com.ojt_final.office.service.module.LinkService;
import com.ojt_final.office.service.module.PartnerProductService;
import com.ojt_final.office.service.module.StandardProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkChangeService {

    private final LinkService linkService;
    private final StandardProductService standardProductService;
    private final PartnerProductService partnerProductService;

    @Transactional
    public BaseResponse create(CreateLinkRequest createLinkRequest) {
        int row = linkService.create(createLinkRequest);
        int isLinkedRow = partnerProductService.updateAllIsLinked(true, createLinkRequest.getPartnerProductCodes());
        int changedStandard = standardProductService.updateLinkedChange();
        log.info("create link={}, updateIsLinked={}, updateStandard={}", row, isLinkedRow, changedStandard);
        return null;
    }
}
