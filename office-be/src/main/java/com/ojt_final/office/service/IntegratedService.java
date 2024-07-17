package com.ojt_final.office.service;

import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.response.CreateLinkResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.service.module.LinkService;
import com.ojt_final.office.service.module.PartnerProdService;
import com.ojt_final.office.service.module.StandardProdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class IntegratedService {

    private final LinkService linkService;
    private final StandardProdService standardProdService;
    private final PartnerProdService partnerProdService;


    public CreateLinkResponse createLink(CreateLinkRequest createLinkRequest) {
        List<String> partnerProdCodes = createLinkRequest.getPartnerProdCodes();
        Set<Integer> targetStandardCodes = findUniqueStandardCodes(partnerProdCodes, createLinkRequest.getStandardProdCode());

        saveLinkAndUpdatePartners(createLinkRequest, partnerProdCodes);

        int changedStandardCount = standardProdService.integrateChange(targetStandardCodes);

        return CreateLinkResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .createdCount(partnerProdService.updateAllIsLinked(true, partnerProdCodes))
                .changedStandardCount(changedStandardCount)
                .build();
    }

    private Set<Integer> findUniqueStandardCodes(List<String> partnerProdCodes, int standardProdCode) {
        Set<Integer> targetStandardCodes = new HashSet<>(linkService.findStandardCodes(partnerProdCodes));
        targetStandardCodes.add(standardProdCode);
        return targetStandardCodes;
    }

    private void saveLinkAndUpdatePartners(CreateLinkRequest createLinkRequest, List<String> partnerProdCodes) {
        linkService.saveAll(createLinkRequest);
        partnerProdService.updateAllIsLinked(true, partnerProdCodes);
    }
//
//    public BaseResponse deleteLink(DeleteLinkRequest deleteLinkRequest) {
//
//        int linkRow = linkService.deleteAll(deleteLinkRequest);
//
//        List<String> partnerProdCodes = deleteLinkRequest.getPartnerProdCodes();
//        partnerProdService.updateAllIsLinked(false, partnerProdCodes);
//
//        List<Integer> targetStandardCodes = linkService.findStandardCodes(partnerProdCodes);
//        int standardRow = standardProdService.integrateChange(targetStandardCodes);
//        // TODO: 삭제된 개수, 변경된 기준 상품 개수
//        return null;
//    }

//    public UploadExcelResponse uploadPartnerProdByExcel(MultipartFile excelFile) throws IOException {
//        UploadExcelResponse response = partnerProdService.importExcel(excelFile);
//        int changedRow = standardProdService.integrateChange(Set.of());
//        response.setStandardChangedCount(changedRow);
//
//        return response;
//    }
//
//    public void updatePartnerProduct(UpdatePartnerProdRequest request) {
//        PartnerProd partnerProd = request.toEntity();
//
//        if (partnerProdService.update(partnerProd)) {
//            List<Integer> targetStandardCodes = linkService.findStandardCodes(List.of(partnerProd.getCode()));
//            standardProdService.integrateChange(targetStandardCodes);
//        }
//    }
//
//    public void deletePartnerProduct(DeletePartnerProdRequest deletePartnerProdRequest) {
//
//        PartnerProd partnerProd = deletePartnerProdRequest.toEntity();
//        partnerProdService.delete(partnerProd);
//
//        if (partnerProd.isLinked()) {
//            List<Integer> targetStandardCodes = linkService.findStandardCodes(List.of(partnerProd.getCode()));
//            int standardRow = standardProdService.integrateChange(targetStandardCodes);
//        }
//    }
//
//    public void deleteStandardProduct() {
//
//    }
}
