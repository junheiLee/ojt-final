package com.ojt_final.office.controller;

import com.ojt_final.office.dto.request.search.CondParam;
import com.ojt_final.office.dto.response.PartnerProdListResponse;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.service.module.PartnerProdService;
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
public class PartnerProdController {

    private final PartnerProdService partnerProdService;

    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @PostMapping("/upload/excel")
    public UploadExcelResponse uploadExcel(@RequestParam(name = "excelFile") MultipartFile excelFile) throws IOException {

        return partnerProdService.saveExcelData(excelFile);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PartnerProdListResponse getPartnerProds(@ModelAttribute CondParam condParam) {

        return partnerProdService.getResponseProds(condParam);
    }
}
