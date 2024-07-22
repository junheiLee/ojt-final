package com.ojt_final.office.global.dto.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ojt_final.office.global.exception.InvalidManufactureDateException;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;

import static com.ojt_final.office.global.constant.CommonConst.DATE_FORMAT;

/**
 * 지정한 문자열을 {@link java.sql.Date} 객체로 변환하기 위한 클래스.
 * <p>
 * <pre>
 * Example:
 * {
 *   "manufactureDate": "201911"
 * }
 * </pre>
 * <p>
 * If the date string is not in the expected format, an {@link IOException} is
 * thrown.
 * </p>
 */
public class CustomDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try {
            java.util.Date parsedDate = DATE_FORMAT.parse(date);
            return new Date(parsedDate.getTime());
        } catch (ParseException e) {
            throw new InvalidManufactureDateException();
        }
    }
}
