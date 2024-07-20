package com.ojt_final.office.partnerProd;

import com.ojt_final.office.global.dto.BaseResponse;
import com.ojt_final.office.global.dto.search.CondParam;
import com.ojt_final.office.integrate.IntegratedService;
import com.ojt_final.office.integrate.dto.IntegratedResponse;
import com.ojt_final.office.link.dto.UploadExcelResponse;
import com.ojt_final.office.partnerProd.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/partner-products")
@RestController
public class PartnerProdController {

    public static final String ATTACHMENT = "attachment; filename=partner_products.xlsx";

    private final PartnerProdService partnerProdService;
    private final IntegratedService integratedService;

    /**
     * 엑셀 파일 업로드 및 처리 후 파트너 상품을 DB에 저장하는 API
     *
     * @param excelFile the Excel file to be uploaded
     * @return an {@link UploadExcelResponse} containing details about the result of the file processing
     * @throws IOException if an error occurs during file processing
     */
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @PostMapping("/upload/excel")
    public UploadExcelResponse uploadExcel(@RequestParam(name = "excelFile") MultipartFile excelFile) throws IOException {

        return partnerProdService.importExcel(excelFile);
    }

    /**
     * 주어진 조건에 해당하는 파트너 상품들을 Excel 파일에 담아 다운로드 할 수 있는 API
     *
     * @param condParam the conditions to filter the partner products
     * @return a ResponseEntity containing the Excel file as a byte array
     */
    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> downloadExcel(@ModelAttribute CondParam condParam) {

        byte[] excelBytes = partnerProdService.exportExcel(condParam);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

    /**
     * 한 건의 협력사 상품을 DB에 저장하는 API
     *
     * @param createRequest the request containing the partner product data
     * @return a response containing the result code and details of the created partner product
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreatePartnerProdResponse save(@Valid @RequestBody CreatePartnerProdRequest createRequest) {

        return partnerProdService.save(createRequest);
    }

    /**
     * 협력사 코드와 상품 코드로 한 건의 파트너 상품을 조회하는 API
     *
     * @return a response containing the result code and details of the created partner product
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{partnerCode}/{prodCode}")
    public GetPartnerProdResponse get(@PathVariable(value = "partnerCode") String partnerCode,
                                      @PathVariable(value = "prodCode") String prodCode) {

        return partnerProdService.get(partnerCode, prodCode);
    }

    /**
     * 주어진 조건에 해당하는 파트너 상품 목록을 조회하는 API
     *
     * @param condParam the conditions to filter the partner products
     * @return a response containing the list of partner products
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PartnerProdsResponse getList(@ModelAttribute CondParam condParam) {

        return partnerProdService.searchWithCount(condParam);
    }

    /**
     * 협력사 상품 업데이트 API
     * <p>
     * 링크된 상품의 가격이 변동된 경우, 해당 기준 상품의 최저가를 갱신한다.
     * </p>
     *
     * @param updateRequest the request containing the updatable partner product data
     * @return a response containing the result code and message
     */
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    public BaseResponse edit(UpdatePartnerProdRequest updateRequest) {

        return integratedService.updatePartnerProduct(updateRequest);
    }

    /**
     * 기준 상품과 연결 확인 API
     *
     * @param partnerCode the code of the partner associated with the product to check
     * @param prodCode    the code of the product to check
     * @return a {@link BaseResponse} containing the result code indicating the status of the product and
     * whether it is linked or unlinked, or if it does not exist.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{partnerCode}/{prodCode}")
    public BaseResponse checkBeforeDelete(@PathVariable(value = "partnerCode") String partnerCode,
                                          @PathVariable(value = "prodCode") String prodCode) {

        return partnerProdService.checkIfLinked(partnerCode, prodCode);
    }

    /**
     * 협력사 상품 삭제 API
     * <p>
     * 링크된 상품이 삭제되는 경우, 해당 기준 상품의 최저가를 갱신한다.
     * </p>
     *
     * @param partnerCode the code of the partner associated with the product to be deleted
     * @param prodCode    the code of the product to be deleted
     * @return an {@link IntegratedResponse} containing the result code and, if applicable, the number of changed standard products
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{partnerCode}/{prodCode}")
    public IntegratedResponse remove(@PathVariable(value = "partnerCode") String partnerCode,
                                     @PathVariable(value = "prodCode") String prodCode) {

        return integratedService.deletePartnerProduct(partnerCode, prodCode);
    }

}
