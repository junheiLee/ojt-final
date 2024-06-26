package com.ojt_final.office.domain;


import com.ojt_final.office.service.excel.ExcelColumn;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;


@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class StandardProduct implements Uploadable {

    @ExcelColumn(upload = "대분류코드")
    private int categoryCode;
    @ExcelColumn(upload = "대분류명")
    private String categoryName;
    @ExcelColumn(upload = "상품코드")
    private int code;

    @ExcelColumn(upload = "상품명")
    private String name;
    @ExcelColumn(upload = "묶음조건")
    private String bundleCondition;
    @ExcelColumn(upload = "설명")
    private String description;

    @ExcelColumn(upload = "출처")
    private String sOrigin;
    @ExcelColumn(upload = "출처url")
    private String sOriginUrl;

    private String sImageUrl;

    private int lowestPrice;
    private int averagePrice;
    private int partnerCount;

    @Builder
    public StandardProduct(int categoryCode, String categoryName,
                           int code, String name,
                           String bundleCondition, String description,
                           String sOrigin, String sOriginUrl, String sImageUrl,
                           int lowestPrice, int averagePrice,
                           int partnerCount) {

        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.code = code;

        this.name = name;
        this.description = description;
        this.bundleCondition = bundleCondition;

        this.sOrigin = sOrigin;
        this.sOriginUrl = sOriginUrl;
        this.sImageUrl = sImageUrl;

        this.lowestPrice = lowestPrice;
        this.averagePrice = averagePrice;
        this.partnerCount = partnerCount;
    }

    @Override
    public void setValuesFromExcel(Row row) {

        this.categoryCode = (int) row.getCell(0).getNumericCellValue();
        this.code = (int) row.getCell(2).getNumericCellValue();
        this.name = getStringCellValue(row.getCell(3));
        this.bundleCondition = getStringCellValue(row.getCell(4));
        this.description = getStringCellValue(row.getCell(5));
        this.sOrigin = getStringCellValue(row.getCell(9));
        this.sOriginUrl = getStringCellValue(row.getCell(10));

    }
}
