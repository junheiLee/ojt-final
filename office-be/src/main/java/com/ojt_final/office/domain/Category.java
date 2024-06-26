package com.ojt_final.office.domain;

import com.ojt_final.office.service.excel.ExcelColumn;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

@Getter
@EqualsAndHashCode @ToString
@NoArgsConstructor
public class Category implements Uploadable {

    @ExcelColumn(upload ="대분류코드")
    private int code;

    @ExcelColumn(upload ="대분류명")
    private String name;

    @Builder
    public Category(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public void setValuesFromExcel(Row row) {
        this.code = (int) row.getCell(0).getNumericCellValue();
        this.name = row.getCell(1).getStringCellValue();
    }
}
