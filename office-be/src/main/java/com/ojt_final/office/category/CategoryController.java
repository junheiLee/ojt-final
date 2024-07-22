package com.ojt_final.office.category;

import com.ojt_final.office.category.dto.CategoriesResponse;
import com.ojt_final.office.company.dto.PartnersResponse;
import com.ojt_final.office.link.dto.UploadExcelResponse;
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

        return categoryService.importExcel(excelFile);
    }

    /**
     * 카테고리 목록 조회 API
     *
     * @return a {@link PartnersResponse} containing partner list
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CategoriesResponse getList() {
        return categoryService.getList();
    }
}