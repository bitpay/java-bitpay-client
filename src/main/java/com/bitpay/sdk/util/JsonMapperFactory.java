/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class JsonMapperFactory {

    public static JsonMapper create() {
        return JsonMapper
            .builder()
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .build();
    }
}
