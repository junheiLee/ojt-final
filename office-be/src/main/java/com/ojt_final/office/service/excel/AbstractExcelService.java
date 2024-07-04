package com.ojt_final.office.service.excel;

import com.ojt_final.office.domain.ExcelProcessable;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * Excel 파일 관련 처리 추상 서비스 클래스
 * 이 클래스는 Excel 파일을 파싱하고, 생성하는 메서드를 제공한다.
 *
 * @param <T> the type of domain object that implements {@link ExcelProcessable}
 */
public abstract class AbstractExcelService<T extends ExcelProcessable> {

    private final ExcelConverter excelConverter = ExcelConverter.INSTANCE;

    /**
     * 파싱과 생성 대상 도메인을 지정하기 위한 메서드
     *
     * @return 해당 서비스가 다루는 특정 도메인
     * @implSpec 해당 클래스가 다루고 있는 엑셀 처리 가능 도메인을 반환해야 한다.
     */
    protected abstract Class<T> getTargetDomain();

    /**
     * MultipartFile 확장자 확인 후, Excel 파일의 헤더와 바디를 파싱해 객체 리스트로 반환한다.
     *
     * @param file  대상 파일
     * @return  파싱한 객체 리스트
     * @throws IOException  {@code MultipartFile}에서 inputStream을 가져올 때 발생
     */
    protected List<T> parse(MultipartFile file) throws IOException {

        // 파일이 Excel 확장자(.xlsx, .xls)인지 확인
        if (!excelConverter.supports(file.getOriginalFilename())) {
            throw new UnSupportedFileException(ResultCode.NOT_EXCEL_FILE);
        }

        return excelConverter.parse(file.getInputStream(), getTargetDomain());
    }

    /**
     * 엑셀 처리 가능한 객체 리스트를 엑셀 파일 바이트 배열로 반환한다.
     *
     * @param items 엑셀 처리 가능한 객체 리스트
     * @return 엑셀 파일의 바이트 배열
     */
    protected byte[] create(List<T> items) {

        return excelConverter.create(items, getTargetDomain());
    }
}
