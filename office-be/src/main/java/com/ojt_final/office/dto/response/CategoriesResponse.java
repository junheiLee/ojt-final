package com.ojt_final.office.dto.response;

import com.ojt_final.office.domain.Category;
import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoriesResponse extends BaseResponse {

    private final List<Category> categories;

    @Builder
    public CategoriesResponse(ResultCode code, List<Category> categories) {
        super(code);
        this.categories = categories;
    }
}
