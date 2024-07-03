package com.ojt_final.office.service;

import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.response.BaseResponse;
import com.ojt_final.office.service.module.LinkService;
import com.ojt_final.office.service.module.PartnerProdService;
import com.ojt_final.office.service.module.StandardProdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkIntegratedService {

    private final LinkService linkService;
    private final StandardProdService standardProdService;
    private final PartnerProdService partnerProdService;

    @Transactional
    public BaseResponse create(CreateLinkRequest createLinkRequest) {
        int createdLinkCount = linkService.create(createLinkRequest);

        int linkedPartnerProductCount
                = partnerProdService.updateAllIsLinked(true, createLinkRequest.getPartnerProductCodes());

        List<Integer> linkedChangeCodes = List.of(createLinkRequest.getStandardProductCode());
        int changedStandardProductCount = standardProdService.integrateChange(linkedChangeCodes);
        return null;
    }

    @Transactional
    public BaseResponse delete(List<String> deleteProductCodes) {
        List<Integer> linkedDeleteStandardCodes = linkService.findAllByProductCodes(deleteProductCodes);
        int deletedLinkCount = linkService.delete(deleteProductCodes);

        int unLinkedPartnerProductCount = partnerProdService.updateAllIsLinked(false, deleteProductCodes);
        int changedStandardProductCount = standardProdService.integrateChange(linkedDeleteStandardCodes);
        return null;
    }
}
