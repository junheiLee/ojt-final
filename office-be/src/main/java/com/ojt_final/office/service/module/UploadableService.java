package com.ojt_final.office.service.module;

import com.ojt_final.office.domain.Uploadable;
import com.ojt_final.office.dto.response.UploadExcelResponse;

import java.util.List;

public interface UploadableService<T extends Uploadable> {

    UploadExcelResponse saveAll(List<T> items);

    Class<T> getTarget();
}
