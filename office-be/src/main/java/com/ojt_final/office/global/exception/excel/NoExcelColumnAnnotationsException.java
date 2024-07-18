package com.ojt_final.office.global.exception.excel;

import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.exception.CustomException;

/**
 * This exception is thrown when the necessary Excel column annotations are missing
 * in the provided Excel file's header.
 *
 * <p>
 * This class extends {@link CustomException} and provides a specific
 * {@link ResultCode} for missing Excel column annotations.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * if (!missingHeaders.isEmpty()) {
 *      throw new NoExcelColumnAnnotationsException();
 * }
 * }
 * </pre>
 * </p>
 *
 * @see CustomException
 * @see ResultCode
 */
public class NoExcelColumnAnnotationsException extends CustomException {

    public NoExcelColumnAnnotationsException() {
        super(ResultCode.MISSING_HEADERS);
    }
}
