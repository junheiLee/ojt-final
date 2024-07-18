package com.ojt_final.office.global.exception;

import com.ojt_final.office.global.constant.ResultCode;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException() {
        super(ResultCode.RESOURCE_NOT_FOUND);
    }
}
