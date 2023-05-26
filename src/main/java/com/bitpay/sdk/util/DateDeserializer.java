/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The type Date deserializer.
 */
public class DateDeserializer extends JsonDeserializer<Long> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * This method deserialize Date according with format yyyy-MM-dd'T'HH:mm:ss.SSS'Z.
     *
     * @param jp JsonParser
     * @param dc DeserializationContext
     * @return Long eg. 233345223232L
     * @throws IOException IOException
     */
    @Override
    public Long deserialize(
        JsonParser jp,
        DeserializationContext dc
    ) throws IOException {
        try {
            return DATE_FORMAT.parse(jp.getText()).getTime();
        } catch (ParseException e) {
            throw new IOException(e);
        }
    }
}
