/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * The type Json mapper factory.
 */
public class JsonMapperFactory {

    /**
     * Create json mapper.
     *
     * @return the json mapper
     */
    public static JsonMapper create() {
        return JsonMapper
            .builder()
            .addModule(new JavaTimeModule())
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();
    }
}
