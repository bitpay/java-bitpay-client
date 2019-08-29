package com.bitpay.sdk.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateDeserializer extends JsonDeserializer<Long> {
    private static final SimpleDateFormat _dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @Override
    public Long deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        Long l = 0l;
        try {
            l = (_dateFormatter.parse(jp.getText())).getTime();
        } catch (ParseException e) {
            throw new IOException(e);
        }
        return l;
    }
}	
