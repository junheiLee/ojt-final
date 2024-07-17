package com.ojt_final.office.controller;

import com.ojt_final.office.dto.request.CreatePartnerProdRequest;
import com.ojt_final.office.dto.request.search.CondParam;
import com.ojt_final.office.dto.response.CreatePartnerProdResponse;
import com.ojt_final.office.dto.response.PartnerProdListResponse;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.service.module.PartnerProdService;
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
     * 한 건의 파트너 상품을 DB에 저장하는 API
     *
     * @param createPartnerProdRequest
     * @return
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreatePartnerProdResponse save(@RequestBody CreatePartnerProdRequest createPartnerProdRequest) throws IOException {
        log.info("requestBody={}", createPartnerProdRequest);

        return partnerProdService.save(createPartnerProdRequest);
    }

    /**
     * 주어진 조건에 해당하는 파트너 상품 목록을 가져오는 API
     *
     * @param condParam the conditions to filter the partner products
     * @return a response containing the list of partner products
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PartnerProdListResponse getPartnerProds(@ModelAttribute CondParam condParam) {

        return partnerProdService.searchWithCount(condParam);
    }

}
