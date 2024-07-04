package com.ojt_final.office.domain;


import com.ojt_final.office.service.excel.ExcelColumn;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import java.sql.Date;


@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class StandardProd implements ExcelProcessable {

    @ExcelColumn(upload = "대분류코드")
    private int categoryCode;
    @ExcelColumn(download = "카테고리")
    private String categoryName;
    @ExcelColumn(upload = "상품코드")
    private int code;

    @ExcelColumn(upload = "상품명", download = "상품명")
    private String name;
    @ExcelColumn(upload = "묶음조건")
    private String bundleCondition;
    @ExcelColumn(upload = "설명")
    private String description;
    @ExcelColumn(upload = "제조일자")
    private Date manufactureDate;

    @ExcelColumn(upload = "출처")
    private String sOrigin;
    @ExcelColumn(upload = "출처url")
    private String sOriginUrl;

    private String sImageUrl;

    @ExcelColumn(download = "통합최저가")
    private int minPrice;
    @ExcelColumn(download = "PC 최저가")
    private int minPcPrice;
    @ExcelColumn(download = "모바일최저가")
    private int minMobilePrice;
    @ExcelColumn(download = "평균가")
    private int avgPrice;
    @ExcelColumn(download = "업체")
    private int partnerCount;

    @Builder
    public StandardProd(int categoryCode, String categoryName,
                        int code, String name,
                        String description, String bundleCondition, Date manufactureDate,
                        String sOrigin, String sOriginUrl, String sImageUrl,
                        int minPrice, int minPcPrice, int minMobilePrice, int avgPrice,
                        int partnerCount) {

        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.code = code;

        this.name = name;
        this.bundleCondition = bundleCondition;
        this.description = description;
        this.manufactureDate = manufactureDate;

        this.sOrigin = sOrigin;
        this.sOriginUrl = sOriginUrl;
        this.sImageUrl = sImageUrl;

        this.minPrice = minPrice;
        this.minPcPrice = minPcPrice;
        this.minMobilePrice = minMobilePrice;
        this.avgPrice = avgPrice;
        this.partnerCount = partnerCount;
    }

    @Override
    public void setValuesFromExcel(Row row) {

        this.categoryCode = (int) row.getCell(0).getNumericCellValue();
        this.code = (int) row.getCell(2).getNumericCellValue();
        this.name = getStringCellValue(row.getCell(3));
        this.bundleCondition = getStringCellValue(row.getCell(4));
        this.description = getStringCellValue(row.getCell(5));
        this.sOrigin = getStringCellValue(row.getCell(6));
        this.sOriginUrl = getStringCellValue(row.getCell(7));
        this.manufactureDate = Date.valueOf(getString((int) row.getCell(8).getNumericCellValue()));

    }

    private static String getString(int manufacture) {

        return String.format("%s-%s-01", manufacture / 100, manufacture % 100);
    }
}
