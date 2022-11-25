/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

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
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .build();
    }
}
