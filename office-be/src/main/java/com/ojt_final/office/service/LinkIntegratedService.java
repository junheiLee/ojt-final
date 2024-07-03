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

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LinkIntegratedService {

    private final LinkService linkService;
    private final StandardProductService standardProductService;
    private final PartnerProductService partnerProductService;

    @Transactional
    public BaseResponse create(CreateLinkRequest createLinkRequest) {
        int createdLinkCount = linkService.create(createLinkRequest);

        int linkedPartnerProductCount
                = partnerProductService.updateAllIsLinked(true, createLinkRequest.getPartnerProductCodes());

        List<Integer> linkedChangeCodes = List.of(createLinkRequest.getStandardProductCode());
        int changedStandardProductCount = standardProductService.integrateChange(linkedChangeCodes);
        return null;
    }

    @Transactional
    public BaseResponse delete(List<String> deleteProductCodes) {
        List<Integer> linkedDeleteStandardCodes = linkService.findAllByProductCodes(deleteProductCodes);
        int deletedLinkCount = linkService.delete(deleteProductCodes);

        int unLinkedPartnerProductCount = partnerProductService.updateAllIsLinked(false, deleteProductCodes);
        int changedStandardProductCount = standardProductService.integrateChange(linkedDeleteStandardCodes);
        return null;
    }
}
