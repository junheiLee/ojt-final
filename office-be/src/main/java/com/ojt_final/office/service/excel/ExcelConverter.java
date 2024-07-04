package com.ojt_final.office.service.excel;


import com.ojt_final.office.domain.Uploadable;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
     * 해당 파일이 Excel 확장자(.xlsx, .xls)인지 확인
     *
     * @param fileName 대상 파일 풀네임 (multipartFile.getOriginalFilename())
     * @return 처리할 수 있는 엑셀 파일이면 True 반환
     */
    public boolean supports(String fileName) {

        String extension = FilenameUtils.getExtension(fileName);
        return Arrays.asList(EXCEL_EXTENSIONS).contains(extension);
    }

    /**
     * 엑셀 파일을 읽어 해당 도메인과 일치하는 헤더인지 확인 후 객체로 변경
     *
     * @param inputStream  대상 엑셀 파일의 inputStream
     * @param targetDomain 객체 타입
     * @param <T>          엑셀 파일을 읽어 db에 저장할 수 있는 객체
     * @return 객체 리스트
     */
    public <T extends Uploadable> List<T> parse(InputStream inputStream,
                                                Class<T> targetDomain) throws IOException {

        Workbook wb = WorkbookFactory.create(inputStream);
        Sheet sheet = wb.getSheetAt(START_INDEX); // sheet 한 개

        validHeaders(sheet.getRow(START_INDEX), getFields(Act.UPLOAD, targetDomain)); // 첫 행의 정보로 타겟 도메인에 저장할 수 있는 헤더인지 확인
        List<T> items = parseBody(sheet, targetDomain);

        wb.close();
        return items;
    }

    private void validHeaders(Row headerRow, List<String> domainFields) {

        List<String> excelHeaders = new ArrayList<>();
        headerRow.cellIterator()
                .forEachRemaining(
                        eachCell -> excelHeaders.add(eachCell.getStringCellValue())
                );

        if (!new HashSet<>(excelHeaders).containsAll(domainFields)) {
            throw new NoExcelColumnAnnotationsException(ResultCode.NOT_HAVE_DOMAIN);
        }
    }

    private <T extends Uploadable> List<T> parseBody(Sheet sheet, Class<T> targetDomain) {

        List<T> items = new ArrayList<>();

        for (Row row : sheet) {

            if (row.getRowNum() == START_INDEX) { // 최초 Row는 개별 값이 아닌 헤더값
                continue;
            }
            items.add(getItemFromRow(row, targetDomain));
        }
        return items;
    }

    private <T extends Uploadable> T getItemFromRow(Row row, Class<T> domain) {

        try {
            T item = domain.getDeclaredConstructor().newInstance();
            // 도메인이 구현한 Uploadable 인터페이스의 setValuesFromExcel 함수를 이름으로 가져와 사용
            domain.getMethod(SET_VALUES_FROM_EXCEL, Row.class).invoke(item, row);
            return item;

        } catch (Exception e) {
            e.printStackTrace();
            // T 타입을 Uploadable 인터페이스 상속으로 규정해 해당 없음
            throw new ExcelInternalException("해당 없음", e);

        }
    }

    /**
     * 객체 리스트를 행에 기재한 엑셀 파일 생성
     *
     * @param items        대상 객체 리스트
     * @param targetDomain 객체 타입
     * @return 엑셀 파일을 byte[]로 반환
     */
    public <T> byte[] create(List<T> items, Class<T> targetDomain) {

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(targetDomain.getName());

        renderHeaders(sheet.createRow(START_INDEX), getFields(Act.DOWNLOAD, targetDomain));  // 첫 행 헤더 정보 기입
        renderBody(sheet, items, targetDomain);

        // 다운로드하는 byte[] 생성 및 리턴
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeAndCloseWorkbookHandleIOException(wb, out);
        return out.toByteArray();
    }

    private void renderHeaders(Row headerRow, List<String> fieldNames) {

        int cellIdx = START_INDEX;
        for (String header : fieldNames) {
            headerRow.createCell(cellIdx, CellType.STRING).setCellValue(header);
            cellIdx++;
        }
    }

    private <T> void renderBody(Sheet sheet, List<T> items, Class<T> targetDomain) {
        int rowIdx = START_INDEX;

        for (T item : items) {
            rowIdx++;
            int cellIdx = START_INDEX;
            Row row = sheet.createRow(rowIdx);

            for (Field field : targetDomain.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(ExcelColumn.class)
                        || field.getAnnotation(ExcelColumn.class).download().isBlank()) {
                    continue;
                }
                createCell(row, cellIdx, item, field);
                cellIdx++;
            }
        }
    }

    private void writeAndCloseWorkbookHandleIOException(Workbook wb, ByteArrayOutputStream out) {
        try {
            wb.write(out);
            wb.close();

        } catch (IOException e) {
            throw new ExcelInternalException("Workbook 오류", e);
        }
    }

    private <T> void createCell(Row row, int cellIdx, T item, Field field) {

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

    private <T> List<String> getFields(Act behave, Class<T> domain) {

        List<String> clazzFields = new ArrayList<>();

        switch (behave) {
            case UPLOAD -> Arrays.stream(domain.getDeclaredFields())
                    .filter(e -> e.isAnnotationPresent(ExcelColumn.class))
                    .filter(e -> !e.getAnnotation(ExcelColumn.class).upload().isBlank())
                    .forEach(e -> clazzFields.add(e.getAnnotation(ExcelColumn.class).upload()));

            case DOWNLOAD -> Arrays.stream(domain.getDeclaredFields())
                    .filter(e -> e.isAnnotationPresent(ExcelColumn.class))
                    .filter(e -> !e.getAnnotation(ExcelColumn.class).download().isBlank())
                    .forEach(e -> clazzFields.add(e.getAnnotation(ExcelColumn.class).download()));
        }

        return clazzFields;
    }

}
