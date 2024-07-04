package com.ojt_final.office.dto.response;

import com.ojt_final.office.domain.StandardProd;
import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class StandardProdListResponse extends BaseResponse {

    private int totalItemsCount;
    private List<StandardProd> prods;

    @Builder
    public StandardProdListResponse(ResultCode resultCode,
                                    int totalItemsCount, List<StandardProd> prods) {
        super(resultCode);
        this.totalItemsCount = totalItemsCount;
        this.prods = prods;
    }
}
