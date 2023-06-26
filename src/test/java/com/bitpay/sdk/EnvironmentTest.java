/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnvironmentTest {

    @Test
    public void it_should_return_environment_from_env_value() {
        Assertions.assertEquals(Environment.PROD, Environment.fromValue("Prod"));
    }
}
