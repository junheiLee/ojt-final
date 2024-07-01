package com.ojt_final.office.controller;

import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.service.ExcelUploadService;
import com.ojt_final.office.service.module.StandardProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/standard-products")
@RestController
public class StandardProductController {

    private final ExcelUploadService excelUploadService;
    private final StandardProductService standardService;

    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @PostMapping("/upload/excel")
    public UploadExcelResponse uploadExcel(@RequestParam(name = "excelFile") MultipartFile excelFile) throws IOException {

        return excelUploadService.saveExcelData(excelFile, standardService.getTarget());
    }

}
