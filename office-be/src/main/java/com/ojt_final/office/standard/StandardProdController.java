package com.ojt_final.office.standard;

import com.ojt_final.office.global.dto.search.CondParam;
import com.ojt_final.office.link.dto.UploadExcelResponse;
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

    private final StandardProdService standardProdService;

    /**
     * 엑셀 파일 업로드 및 처리 후 기준 상품을 DB에 저장하는 API
     *
     * @param excelFile the Excel file to be uploaded
     * @return the response after processing the Excel file
     * @throws IOException if an error occurs during file processing
     */
    @ResponseStatus(HttpStatus.MULTI_STATUS)
    @PostMapping("/upload/excel")
    public UploadExcelResponse uploadExcel(@RequestParam(name = "excelFile") MultipartFile excelFile) throws IOException {

        return standardProdService.importExcel(excelFile);
    }

    /**
     * 주어진 조건에 해당하는 기준 상품들을 Excel 파일에 담아 다운로드 할 수 있는 API
     *
     * @param condParam the conditions to filter the standard products
     * @return a ResponseEntity containing the Excel file as a byte array
     */
    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> downloadExcel(@ModelAttribute CondParam condParam) {

        byte[] excelBytes = standardProdService.exportExcel(condParam);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, ATTACHMENT);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

    /**
     * 주어진 조건에 해당하는 기준 상품 목록을 가져오는 API
     *
     * @param condParam the conditions to filter the standard products
     * @return a response containing the list of standard products
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public StandardProdListResponse getStandardProds(@ModelAttribute CondParam condParam) {

        return standardProdService.getResponseProds(condParam);
    }

}
