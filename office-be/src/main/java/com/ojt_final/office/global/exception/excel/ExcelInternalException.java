package com.ojt_final.office.global.exception.excel;

/**
 * This exception is thrown when an internal error occurs during the processing of Excel files.
 *
 * <p>
 * This class extends {@link RuntimeException} and allows for a custom error message and
 * a nested exception to be provided.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * try {
 *     // Code that processes Excel file
 * } catch (SomeSpecificException e) {
 *     throw new ExcelInternalException("Error processing Excel file", e);
 * }
 * }
 * </pre>
 * </p>
 *
 * @see RuntimeException
 */
public class ExcelInternalException extends RuntimeException {

    public ExcelInternalException(String message, Throwable e) {
        super(message, e);
    }
}
