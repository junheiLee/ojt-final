package com.ojt_final.office.service;

import com.ojt_final.office.domain.Uploadable;
import com.ojt_final.office.dto.response.UploadExcelResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import com.ojt_final.office.global.exception.excel.UnSupportedFileException;
import com.ojt_final.office.service.excel.ExcelConverter;
import com.ojt_final.office.service.module.UploadableService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelUploadService {

    private final static ExcelConverter excelConverter = ExcelConverter.INSTANCE;
    private final Map<Class<? extends Uploadable>, UploadableService<? extends Uploadable>> serviceMap = new HashMap<>();

    public ExcelUploadService(List<UploadableService<? extends Uploadable>> services) {
        for (UploadableService<? extends Uploadable> service : services) {
            serviceMap.put(service.getTarget(), service);
        }
    }

    public <T extends Uploadable> UploadExcelResponse saveExcelData(MultipartFile excelFile,
                                                                    Class<T> targetDomain) throws IOException {

        // 파일이 Excel 확장자(.xlsx, .xls)인지 확인
        if (!excelConverter.supports(excelFile.getOriginalFilename())) {
            throw new UnSupportedFileException(ResultCode.NOT_EXCEL_FILE);
        }

        List<T> items = excelConverter.parse(excelFile.getInputStream(), targetDomain);
        UploadableService<T> service = getService(targetDomain);

        return service.saveAll(items);
    }

    @SuppressWarnings("unchecked")
    private <T extends Uploadable> UploadableService<T> getService(Class<T> domainClass) {
        return (UploadableService<T>) serviceMap.get(domainClass);
    }

}
