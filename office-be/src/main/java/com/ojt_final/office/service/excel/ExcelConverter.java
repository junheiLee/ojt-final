package com.ojt_final.office.service.excel;


import com.ojt_final.office.domain.ExcelProcessable;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.ExcelInternalException;
import com.ojt_final.office.global.exception.excel.NoExcelColumnAnnotationsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.*;
import java.util.function.Function;

import static com.ojt_final.office.service.excel.ExcelConst.*;


@Slf4j
public class ExcelConverter {

    public static final ExcelConverter INSTANCE = new ExcelConverter();
    private static boolean created;

    private ExcelConverter() {
        // 리플렉션으로 생성자 호출하는 경우 방지
        if (created) {
            throw new UnsupportedOperationException("can't be created by constructor.");
        }
        created = true;
    }

    /**
     * 해당 파일이 Excel 확장자(.xlsx, .xls)인지 확인한다.
     *
     * @param fileName the full name of the file (e.g., multipartFile.getOriginalFilename())
     * @return true if the file is an Excel file, false otherwise
     */
    public boolean supports(String fileName) {

        String extension = FilenameUtils.getExtension(fileName);
        return Arrays.asList(EXCEL_EXTENSIONS).contains(extension);
    }

    /**
     * 엑셀 파일을 읽어 해당 도메인과 일치하는 헤더인지 확인 후 객체 리스트로 변환해 반환한다.
     *
     * @param inputStream  the input stream of the Excel file
     * @param targetDomain the class of the domain object
     * @param <T>          the type of the excel-processable domain object to parse and save
     * @return a list of domain objects parsed from Excel file
     */
    public <T extends ExcelProcessable> List<T> read(InputStream inputStream, Class<T> targetDomain) {

        Workbook wb = createWorkbook(inputStream);
        Sheet sheet = wb.getSheetAt(START_INDEX); // 엑셀 파일의 첫 번째 sheet

        validateHeaderRows(sheet.getRow(START_INDEX), getHeaderNames(Act.UPLOAD, targetDomain)); // 첫 행의 헤더로 DB 저장 가능 여부 확인
        List<T> items = readDataRows(sheet, targetDomain);

        closeWorkbook(wb);
        return items;
    }

    private void validateHeaderRows(Row headerRow, List<String> domainHeaders) {

        List<String> excelHeaders = new ArrayList<>();
        headerRow.cellIterator().forEachRemaining(cell -> excelHeaders.add(cell.getStringCellValue()));

        Set<String> missingHeaders = new HashSet<>(domainHeaders);
        missingHeaders.removeAll(excelHeaders);

        if (!missingHeaders.isEmpty()) {
            log.error("[ExcelConverter] missing headers: {}", missingHeaders);
            throw new NoExcelColumnAnnotationsException(ResultCode.MISSING_HEADERS);
        }
    }

    private <T extends ExcelProcessable> List<T> readDataRows(Sheet sheet, Class<T> targetDomain) {

        List<T> items = new ArrayList<>();

        for (Row row : sheet) {

            if (row.getRowNum() == START_INDEX) { // skip header row
                continue;
            }
            items.add(readRow(row, targetDomain));
        }
        return items;
    }

    private <T extends ExcelProcessable> T readRow(Row row, Class<T> domain) {

        try {
            T item = domain.getDeclaredConstructor().newInstance();
            // ExcelProcessable 인터페이스의 추상 메서드 호출
            domain.getMethod(SET_VALUES_FROM_EXCEL, Row.class).invoke(item, row);
            return item;

        } catch (Exception e) {
            throw new ExcelInternalException("해당 없음", e);
        }
    }

    /**
     * 객체 리스트를 행에 기재한 엑셀 파일을 생성해 byte 배열로 반환한다.
     *
     * @param items        the list of domain objects to create the Excel file
     * @param targetDomain the class of the domain object
     * @param <T>          the type of the excel-processable domain object to create the Excel file
     * @return the Excel file as a byte array from object list
     */
    public <T extends ExcelProcessable> byte[] write(List<T> items, Class<T> targetDomain) {

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(targetDomain.getName());

        writeHeaderRows(sheet.createRow(START_INDEX), getHeaderNames(Act.DOWNLOAD, targetDomain));  // 첫 행 헤더 정보 기입
        writeDataRows(sheet, items, targetDomain);

        // 다운로드하는 byte[] 생성 및 리턴
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeWorkbook(wb, out);
        closeWorkbook(wb);
        return out.toByteArray();
    }

    private void writeHeaderRows(Row headerRow, List<String> fieldNames) {

        int cellIdx = START_INDEX;

        for (String header : fieldNames) {
            headerRow.createCell(cellIdx, CellType.STRING).setCellValue(header);
            cellIdx++;
        }
    }

    private <T> void writeDataRows(Sheet sheet, List<T> items, Class<T> targetDomain) {
        int rowIdx = START_INDEX;

        for (T item : items) {
            rowIdx++;
            Row row = sheet.createRow(rowIdx);
            writeRow(row, item, targetDomain);
        }
    }

    private <T> void writeRow(Row row, T item, Class<T> targetDomain) {

        int cellIdx = START_INDEX;

        for (Field field : targetDomain.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(ExcelColumn.class) ||
                    field.getAnnotation(ExcelColumn.class).download().isBlank()) {
                continue;
            }
            writeCell(row, cellIdx, item, field);
            cellIdx++;
        }
    }

    private <T> void writeCell(Row row, int cellIdx, T item, Field field) {

        String type = item.getClass().getTypeName();

        try {
            switch (type) {
                case INT -> row.createCell(cellIdx, CellType.NUMERIC).setCellValue((double) field.get(item));
                case DATE -> row.createCell(cellIdx, CellType.FORMULA).setCellValue((Date) field.get(item));
                default -> row.createCell(cellIdx, CellType.STRING).setCellValue(String.valueOf(field.get(item)));
            }

        } catch (IllegalAccessException e) {
            // field.setAccessible(true) 후 사용하므로 field.get 메소드의 Checked Exception(IllegalAccessException) 치환
            throw new ExcelInternalException("해당 없음", e);
        }
    }

    private <T> List<String> getHeaderNames(Act action, Class<T> domain) {

        List<String> fieldNames = new ArrayList<>();

        switch (action) {
            case UPLOAD -> addHeaderNames(domain, fieldNames, ExcelColumn::upload);
            case DOWNLOAD -> addHeaderNames(domain, fieldNames, ExcelColumn::download);
        }

        return fieldNames;
    }

    private <T> void addHeaderNames(Class<T> domain, List<String> fieldNames, Function<ExcelColumn, String> extractor) {

        Arrays.stream(domain.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
                .map(field -> extractor.apply(field.getAnnotation(ExcelColumn.class)))
                .filter(value -> !value.isBlank())
                .forEach(fieldNames::add);
    }

    private Workbook createWorkbook(InputStream inputStream) {
        try {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new ExcelInternalException("[Workbook] create error: ", e);
        }
    }

    private void writeWorkbook(Workbook wb, ByteArrayOutputStream out) {
        try {
            wb.write(out);

        } catch (IOException e) {
            throw new ExcelInternalException("[Workbook] write error: ", e);
        }
    }

    private void closeWorkbook(Workbook wb) {
        try {
            wb.close();

        } catch (IOException e) {
            throw new ExcelInternalException("[Workbook] close error: ", e);
        }
    }

}
