/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JsonMapperFactoryTest {

    @Test
    public void it_should_test_create_object_mapper() {
        Assertions.assertSame(JsonMapper.class, JsonMapperFactory.create().getClass());
    }
}
