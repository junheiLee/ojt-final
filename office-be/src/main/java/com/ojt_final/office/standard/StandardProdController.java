package com.ojt_final.office.standard;

import com.ojt_final.office.global.dto.BaseResponse;
import com.ojt_final.office.global.dto.search.CondParam;
import com.ojt_final.office.link.dto.UploadExcelResponse;
import com.ojt_final.office.standard.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
     * 한 건의 기준 상품을 DB에 저장하는 API
     *
     * @param createRequest the request containing the standard product data
     * @return a response containing the result code
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponse save(@Valid @RequestPart(value = "createDto") CreateStandardProdRequest createRequest,
                             @RequestPart(value = "image") MultipartFile imageFile) {

        return standardProdService.save(createRequest, imageFile);
    }

    /**
     * 한 건의 기준 상품 조회 API
     *
     * @param code the code of the product. This parameter is used to identify the specific product.
     * @return a response containing the result code and details of the standard product
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{code}")
    public GetStandardProdResponse get(@Valid @PathVariable @Positive(message = "NEGATIVE") int code) {

        return standardProdService.get(code);
    }

    /**
     * 주어진 조건에 해당하는 기준 상품 목록을 가져오는 API
     *
     * @param condParam the conditions to filter the standard products
     * @return a response containing the list of standard products
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public StandardProdsResponse getList(@ModelAttribute CondParam condParam) {

        return standardProdService.searchWithCount(condParam);
    }

    /**
     * 기준 상품 수정 API
     *
     * @param code          the code of the product. This parameter is used to identify the specific product.
     * @param updateRequest the request containing the updatable standard product data
     * @return a response containing the list of standard products
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{code}")
    public BaseResponse edit(@PathVariable @Positive(message = "NEGATIVE") int code,
                             @Valid @RequestPart(value = "updateDto") UpdateStandardProdRequest updateRequest,
                             @RequestPart(value = "image") MultipartFile imageFile) {

        return standardProdService.edit(code, updateRequest, imageFile);
    }

    /**
     * 기준 상품 삭제
     *
     * @param code the code of the product. This parameter is used to identify the specific product.
     * @return a response containing the list of standard products
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{code}")
    public DeleteStandardProdResponse remove(@PathVariable Integer code) {

        return standardProdService.delete(code);
    }

}
