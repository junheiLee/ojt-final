package com.ojt_final.office.integrate;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.integrate.dto.IntegratedResponse;
import com.ojt_final.office.link.LinkService;
import com.ojt_final.office.link.dto.CreateLinkRequest;
import com.ojt_final.office.link.dto.CreateLinkResponse;
import com.ojt_final.office.partnerProd.PartnerProd;
import com.ojt_final.office.partnerProd.PartnerProdService;
import com.ojt_final.office.partnerProd.dto.UpdatePartnerProdRequest;
import com.ojt_final.office.standard.StandardProdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class IntegratedService {

    private final LinkService linkService;
    private final StandardProdService standardProdService;
    private final PartnerProdService partnerProdService;

    /**
     * 협력사 상품을 수정하고, 링크된 상품의 가격이 변경된 경우 해당 기준 상품의 최저가 및 업체를 갱신한다.
     *
     * @param request the request containing the partner product data to be updated
     * @return an {@link IntegratedResponse} indicating the result of the update and whether a renewal was performed
     */
    public IntegratedResponse updatePartnerProduct(UpdatePartnerProdRequest request) {

        PartnerProd partnerProd = request.toEntity();
        boolean requireRenewal = partnerProdService.requiresRenewalAfterUpdate(partnerProd);

        if (requireRenewal) {
            return renew(List.of(partnerProd.getCode()));
        }
        return IntegratedResponse.builder()
                .code(ResultCode.SUCCESS)
                .build();
    }

    /**
     * 지정된 파트너 상품 코드와 연결된 기준 상품의 최저가와 업체를 갱신한다.
     *
     * @param partnerProdCodes the list of partner product codes for which to find related standard codes
     * @return an {@link IntegratedResponse} containing the result code and the count of changed standard products
     */
    private IntegratedResponse renew(List<String> partnerProdCodes) {
        List<Integer> targetStandardCodes = linkService.findStandardCodes(partnerProdCodes);
        int changedStandardCount = standardProdService.renewIntegrated(targetStandardCodes);

        return IntegratedResponse.builder()
                .code(ResultCode.RENEW)
                .changedStandardCount(changedStandardCount)
                .build();
    }

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

    public CreateLinkResponse createLink(CreateLinkRequest createLinkRequest) {
        List<String> partnerProdCodes = createLinkRequest.getPartnerProdCodes();
        List<Integer> targetStandardCodes = linkService.findStandardCodes(partnerProdCodes);
        targetStandardCodes.add(createLinkRequest.getStandardProdCode());

        saveLinkAndUpdatePartners(createLinkRequest, partnerProdCodes);

        int changedStandardCount = standardProdService.renewIntegrated(targetStandardCodes);

        return CreateLinkResponse.builder()
                .resultCode(ResultCode.SUCCESS)
                .createdCount(partnerProdService.updateAllIsLinked(true, partnerProdCodes))
                .changedStandardCount(changedStandardCount)
                .build();
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

//
//    public void deleteStandardProduct() {
//
//    }
}
