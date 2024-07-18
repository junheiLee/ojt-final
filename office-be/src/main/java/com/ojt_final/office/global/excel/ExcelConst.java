package com.ojt_final.office.global.excel;

public final class ExcelConst {

    final static String[] EXCEL_EXTENSIONS = {"xls", "xlsx"};
    final static String SET_VALUES_FROM_EXCEL = "setValuesFromExcel";
    final static int START_INDEX = 0;

    final static String INT = "int";
    final static String DATE = "java.sql.Date";

    enum Act {
        UPLOAD, DOWNLOAD
    }
}
