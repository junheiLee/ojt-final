package com.ojt_final.office.partnerProd.dto;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import com.ojt_final.office.partnerProd.PartnerProd;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Represents a response containing a list of partner products.
 * Extends {@link BaseResponse} to include base response fields.
 */
@ToString(callSuper = true)
@Getter
public class PartnerProdsResponse extends BaseResponse {

    private int totalItemsCount;
    private List<PartnerProd> prods;

    /**
     * Constructs a PartnerProdListResponse with the given result code, total item count, and list of partner products.
     *
     * @param resultCode      the result code indicating the outcome of the request
     * @param totalItemsCount the total number of items
     * @param prods           the list of partner products
     */
    @Builder
    public PartnerProdsResponse(ResultCode resultCode,
                                int totalItemsCount, List<PartnerProd> prods) {
        super(resultCode);
        this.totalItemsCount = totalItemsCount;
        this.prods = prods;
    }
}
