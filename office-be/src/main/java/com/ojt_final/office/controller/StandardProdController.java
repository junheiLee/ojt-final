package com.ojt_final.office.controller;

import com.ojt_final.office.dto.request.search.CondParam;
import com.ojt_final.office.dto.response.StandardProdListResponse;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.service.module.StandardProdService;
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
@RequestMapping("/standard-products")
@RestController
public class StandardProdController {

    public static final String ATTACHMENT = "attachment; filename=standard_products.xlsx";

    private final StandardProdService standardService;

    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @PostMapping("/upload/excel")
    public UploadExcelResponse uploadExcel(@RequestParam(name = "excelFile") MultipartFile excelFile) throws IOException {

        return standardService.saveExcelData(excelFile);
    }

    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> downloadExcel(@ModelAttribute CondParam condParam) {

        byte[] excelBytes = standardService.createExcelFile(condParam);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public StandardProdListResponse getPartnerProds(@ModelAttribute CondParam condParam) {

        return standardService.getResponseProds(condParam);
    }
}
