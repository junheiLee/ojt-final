package com.ojt_final.office.service;

import com.ojt_final.office.domain.PartnerProd;
import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.request.RemoveLinkRequest;
import com.ojt_final.office.dto.request.UpdatePartnerProdRequest;
import com.ojt_final.office.dto.response.BaseResponse;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.service.module.LinkService;
import com.ojt_final.office.service.module.PartnerProdService;
import com.ojt_final.office.service.module.StandardProdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class IntegratedService {

    private final LinkService linkService;
    private final StandardProdService standardProdService;
    private final PartnerProdService partnerProdService;


    public BaseResponse createLink(CreateLinkRequest createLinkRequest) {

        List<String> partnerProdCodes = createLinkRequest.getPartnerProdCodes();
        List<Integer> changedStandardProdCodes = linkService.findStandardCodesByPartnerProdCodes(partnerProdCodes);
        changedStandardProdCodes.add(createLinkRequest.getStandardProdCode());

        int linkRow = linkService.create(createLinkRequest);
        int partnerRow = partnerProdService.updateAllIsLinked(true, partnerProdCodes);
        int standardRow = standardProdService.integrateChange(changedStandardProdCodes);
        // TODO: 각 Row 값을 통해 created, updated, unChanged, failed 추적 로직
        return null;
    }

    public BaseResponse deleteLink(RemoveLinkRequest removeLinkRequest) {

        List<String> partnerProdCodes = removeLinkRequest.getPartnerProdCodes();
        List<Integer> targetStandardCodes = linkService.findStandardCodesByPartnerProdCodes(partnerProdCodes);

        int linkRow = linkService.delete(partnerProdCodes);
        int partnerRow = partnerProdService.updateAllIsLinked(false, partnerProdCodes);
        int standardRow = standardProdService.integrateChange(targetStandardCodes);
        // TODO: 각 Row 값을 통해 created, updated, unChanged, failed 추적 로직
        return null;
    }

    public UploadExcelResponse uploadPartnerProdByExcel(MultipartFile excelFile) throws IOException {
        UploadExcelResponse response = partnerProdService.importExcel(excelFile);
        int changedRow = standardProdService.integrateChange(List.of());
        response.setStandardChangedCount(changedRow);

        return response;
    }

    public void updatePartnerProduct(UpdatePartnerProdRequest request) {
        PartnerProd partnerProd = request.toEntity();
        if (partnerProdService.update(partnerProd)) {
            List<Integer> targetStandardCodes = linkService.findStandardCodesByPartnerProdCodes(List.of(partnerProd.getCode()));
            standardProdService.integrateChange(targetStandardCodes);
        }
    }

    public void deletePartnerProduct() {
        // TODO: 한 번 되물어야댐
    }

}
