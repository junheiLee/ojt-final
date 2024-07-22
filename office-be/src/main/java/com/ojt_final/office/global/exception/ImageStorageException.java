package com.ojt_final.office.global.exception;

import com.ojt_final.office.global.constant.ResultCode;

public class ImageStorageException extends CustomException {

    public ImageStorageException(Throwable cause) {
        super(ResultCode.IMAGE_ERROR);
    }
}
