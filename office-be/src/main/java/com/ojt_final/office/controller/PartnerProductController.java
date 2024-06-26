package com.ojt_final.office.controller;

import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.service.PartnerProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/partner-products")
@RestController
public class PartnerProductController {

    private final PartnerProductService partnerProductService;

    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @PostMapping("/upload/excel")
    public UploadExcelResponse<Object> uploadExcel(@RequestParam(name = "excelFile") MultipartFile excelFile) throws IOException {

        return partnerProductService.saveExcelData(excelFile);
    }

}
