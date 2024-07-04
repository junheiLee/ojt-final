package com.ojt_final.office.controller;

import com.ojt_final.office.dto.request.search.CondParam;
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

    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @PostMapping("/upload/excel")
    public UploadExcelResponse uploadExcel(@RequestParam(name = "excelFile") MultipartFile excelFile) throws IOException {

        return partnerProdService.saveExcelData(excelFile);
    }

    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> downloadExcel(@ModelAttribute CondParam condParam) {

        byte[] excelBytes = partnerProdService.createExcelFile(condParam);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PartnerProdListResponse getPartnerProds(@ModelAttribute CondParam condParam) {

        return partnerProdService.getResponseProds(condParam);
    }
}
