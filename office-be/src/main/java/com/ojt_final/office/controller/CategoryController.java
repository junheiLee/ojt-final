package com.ojt_final.office.controller;

import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.service.module.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 엑셀 파일 업로드 및 처리 후 카테고리를 DB에 저장하는 API
     *
     * @param excelFile the Excel file to be uploaded
     * @return an {@link UploadExcelResponse} containing the number of created, updated,
     * unchanged, and failed records after processing the file
     * @throws IOException if an error occurs during file processing
     */
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @PostMapping("/upload/excel")
    public UploadExcelResponse uploadExcel(@RequestParam(name = "excelFile") MultipartFile excelFile) throws IOException {

        return categoryService.saveExcelData(excelFile);
    }

}
