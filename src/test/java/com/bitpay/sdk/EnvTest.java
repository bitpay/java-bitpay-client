/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnvTest {

    @Test
    public void it_should_returns_environments() {
        Assertions.assertEquals("Test", Env.Test);
        Assertions.assertEquals("Prod", Env.Prod);
    }

    @Test
    public void it_should_returns_env_urls() {
        Assertions.assertEquals("https://test.bitpay.com/", Env.TestUrl);
        Assertions.assertEquals("https://bitpay.com/", Env.ProdUrl);
    }

    @Test
    public void it_should_returns_bitpay_api_version() {
        Assertions.assertEquals("2.0.0", Env.BitpayApiVersion);
    }

    @Test
    public void it_should_returns_bitpay_plugin_info() {
        Assertions.assertEquals("BitPay_Java_Client_v8.6.2", Env.BitpayPluginInfo);
    }

    @Test
    public void it_should_returns_bitpay_api_frame() {
        Assertions.assertEquals("std", Env.BitpayApiFrame);
    }

    @Test
    public void it_should_returns_bitpay_api_frame_version() {
        Assertions.assertEquals("1.0.0", Env.BitpayApiFrameVersion);
    }
}
