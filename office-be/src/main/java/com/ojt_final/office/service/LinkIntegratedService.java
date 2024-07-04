package com.ojt_final.office.service;

import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.request.RemoveLinkRequest;
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
                = partnerProdService.updateAllIsLinked(true, createLinkRequest.getPartnerProdCodes());

        List<Integer> linkedChangeCodes = List.of(createLinkRequest.getStandardProdCode());
        int changedStandardProductCount = standardProdService.integrateChange(linkedChangeCodes);
        return null;
    }

    @Transactional
    public BaseResponse delete(RemoveLinkRequest removeLinkRequest) {

        List<String> partnerProdCodes = removeLinkRequest.getPartnerProdCodes();
        List<Integer> linkedDeleteStandardCodes = linkService.findAllByProdCodes(partnerProdCodes);
        int deletedLinkCount = linkService.delete(partnerProdCodes);

        int unLinkedPartnerProductCount = partnerProdService.updateAllIsLinked(false, partnerProdCodes);
        int changedStandardProductCount = standardProdService.integrateChange(linkedDeleteStandardCodes);
        return null;
    }
}
