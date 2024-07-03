package com.ojt_final.office.domain;

import com.ojt_final.office.service.excel.ExcelColumn;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import java.sql.Date;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class PartnerProd implements Uploadable {

    @ExcelColumn(upload = "대분류코드")
    private int categoryCode;
    @ExcelColumn(upload = "대분류명")
    private String categoryName;
    @ExcelColumn(upload = "협력사코드")
    private String partnerCode;

    @ExcelColumn(upload = "상품코드")
    private String code;
    @ExcelColumn(upload = "상품명")
    private String name;

    @ExcelColumn(upload = "PC가")
    private int pcPrice;
    @ExcelColumn(upload = "모바일가")
    private int mobilePrice;

    @ExcelColumn(upload = "등록일")
    private Date createdAt;

    @ExcelColumn(upload = "url")
    private String url;
    @ExcelColumn(upload = "이미지url")
    private String imageUrl;

    private boolean isLinked;

    @Builder
    public PartnerProd(int categoryCode, String categoryName, String partnerCode,
                       String code, String name,
                       int pcPrice, int mobilePrice,
                       Date createdAt,
                       String url, String imageUrl,
                       boolean isLinked) {

        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.partnerCode = partnerCode;

        this.code = code;
        this.name = name;

        this.pcPrice = pcPrice;
        this.mobilePrice = mobilePrice;

        this.createdAt = createdAt;

        this.url = url;
        this.imageUrl = imageUrl;

        this.isLinked = isLinked;
    }

    @Override
    public void setValuesFromExcel(Row row) {
        this.categoryCode = (int) row.getCell(0).getNumericCellValue();
        this.partnerCode = getStringCellValue(row.getCell(2));

        this.code = getStringCellValue(row.getCell(3));
        this.name = getStringCellValue(row.getCell(4));

        this.pcPrice = (int) row.getCell(5).getNumericCellValue();
        this.mobilePrice = (int) row.getCell(6).getNumericCellValue();

        this.createdAt = new Date(row.getCell(7).getDateCellValue().getTime());
        this.url = getStringCellValue(row.getCell(8));
        this.imageUrl = getStringCellValue(row.getCell(9));
    }
}
