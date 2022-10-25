/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * The type Date serializer.
 */
public class DateSerializer extends JsonSerializer<Long> {
    private static final SimpleDateFormat _dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * This method serialize Date according with format yyyy-MM-dd'T'HH:mm:ss.SSS'Z.
     *
     * @param value Long eg 233345223232L
     * @param jgen JsonGenerator
     * @param provider SerializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(Long value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(_dateFormatter.format(value));
    }
}	
