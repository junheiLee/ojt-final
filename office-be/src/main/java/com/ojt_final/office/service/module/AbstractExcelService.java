package com.ojt_final.office.service.module;

import com.ojt_final.office.domain.Uploadable;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import com.ojt_final.office.service.excel.ExcelConverter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public abstract class AbstractExcelService<T extends Uploadable> {

    private final ExcelConverter excelConverter = ExcelConverter.INSTANCE;

    /**
     * parse 메서드를 활용해 엑셀을 파싱하고, DB에 저장하는 기능
     *
     * @param excelFile
     * @return
     * @throws IOException
     */
    abstract UploadExcelResponse saveExcelData(MultipartFile excelFile) throws IOException;

    /**
     * parse 메서드에서 excelConverter의 target도메인을 지정해주기 위한 메서드.
     *
     * @return 해당 서비스가 다루는 특정 도메인
     */
    abstract Class<T> getTargetDomain();

    /**
     * Upload가능한 Service로서 MultipartFile의 확장자를 확인 후, 파싱해 객체 리스트로 반환
     *
     * @param excelFile
     * @return
     * @throws IOException
     */
    protected List<T> parse(MultipartFile excelFile) throws IOException {

        // 파일이 Excel 확장자(.xlsx, .xls)인지 확인
        if (!excelConverter.supports(excelFile.getOriginalFilename())) {
            throw new UnSupportedFileException(ResultCode.NOT_EXCEL_FILE);
        }

        return excelConverter.parse(excelFile.getInputStream(), getTargetDomain());
    }

    protected <T> byte[] create(List<T> items, Class<T> targetDomain) {

        return excelConverter.create(items, targetDomain);
    }
}
