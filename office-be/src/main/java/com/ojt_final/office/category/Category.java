package com.ojt_final.office.category;

import com.ojt_final.office.global.excel.ExcelColumn;
import com.ojt_final.office.global.excel.ExcelProcessable;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Category implements ExcelProcessable {

    @ExcelColumn(upload = "대분류코드")
    private int code;

    @ExcelColumn(upload = "대분류명")
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
