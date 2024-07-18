package com.ojt_final.office.global.exception.excel;


import com.ojt_final.office.global.constant.ResultCode;
import com.ojt_final.office.global.exception.CustomException;

/**
 * This exception is thrown when a non-supported file type is encountered,
 * specifically when the file is not an Excel file.
 *
 * <p>
 * This class extends {@link CustomException} and provides a specific
 * {@link ResultCode} for unsupported file types.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * if (!isExcelFile(file)) {
 *     throw new UnSupportedFileException();
 * }
 * }
 * </pre>
 * </p>
 *
 * @see CustomException
 * @see ResultCode
 */
public class UnSupportedFileException extends CustomException {

    public UnSupportedFileException() {
        super(ResultCode.NOT_EXCEL_FILE);
    }
}
