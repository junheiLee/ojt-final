package com.ojt_final.office.category.dto;

import com.ojt_final.office.category.Category;
import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
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
