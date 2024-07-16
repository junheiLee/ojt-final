package com.ojt_final.office.service.excel;

import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Excel 파일 관련 처리 인터페이스
 * 이 클래스는 Excel 파일을 파싱하고, 생성하는 메서드를 제공한다.
 *
 * @param <T> the type of domain object that implements {@link ExcelProcessable}
 */
public abstract class ExcelProcessingHandler<T extends ExcelProcessable> {

    ExcelConverter excelConverter = ExcelConverter.INSTANCE;

    /**
     * 파싱과 생성 대상 도메인을 지정하기 위한 메서드
     *
     * @return the Excel-processable domain handled by this service
     * @implSpec This method should return the Excel-processable domain handled by the class.
     */
    protected abstract Class<T> getTargetDomain();

    /**
     * MultipartFile 확장자 확인 후, Excel 파일의 헤더와 바디를 파싱해 객체 리스트로 반환한다.
     *
     * @param file the Excel file to be parsed
     * @return a list of parsed objects
     * @throws IOException if an error occurs while getInputStream() from {@code MultipartFile}
     */
    protected List<T> parse(MultipartFile file) throws IOException {

        // 파일이 Excel 확장자(.xlsx, .xls)인지 확인
        if (!excelConverter.supports(file.getOriginalFilename())) {
            throw new UnSupportedFileException();
        }
        return excelConverter.read(file.getInputStream(), getTargetDomain());
    }

    /**
     * 엑셀 처리 가능한 객체 리스트를 엑셀 파일 바이트 배열로 반환한다.
     *
     * @param items items the list of Excel-processable objects
     * @return a byte array representing the Excel file
     */
    protected byte[] create(List<T> items) {

        return excelConverter.write(items, getTargetDomain());
    }
}