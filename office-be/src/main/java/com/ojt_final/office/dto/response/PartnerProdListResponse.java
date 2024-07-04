package com.ojt_final.office.dto.response;

import com.ojt_final.office.domain.PartnerProd;
import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class PartnerProdListResponse extends BaseResponse {

    private int totalItemsCount;
    private List<PartnerProd> prods;

    @Builder
    public PartnerProdListResponse(ResultCode resultCode,
                                   int totalItemsCount, List<PartnerProd> prods) {
        super(resultCode);
        this.totalItemsCount = totalItemsCount;
        this.prods = prods;
    }
}
