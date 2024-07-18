package com.ojt_final.office.standard;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Represents a response containing a list of standard products.
 * Extends {@link BaseResponse} to include base response fields.
 */
@ToString(callSuper = true)
@Getter
public class StandardProdListResponse extends BaseResponse {

    private int totalItemsCount;
    private List<StandardProd> prods;

    /**
     * Constructs a StandardProdListResponse with the given result code, total item count, and list of standard products.
     *
     * @param resultCode      the result code indicating the outcome of the request
     * @param totalItemsCount the total number of items
     * @param prods           the list of partner products
     */
    @Builder
    public StandardProdListResponse(ResultCode resultCode,
                                    int totalItemsCount, List<StandardProd> prods) {
        super(resultCode);
        this.totalItemsCount = totalItemsCount;
        this.prods = prods;
    }
}
