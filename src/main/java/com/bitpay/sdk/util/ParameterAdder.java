/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.util;

import java.util.List;
import java.util.Objects;
import org.apache.http.message.BasicNameValuePair;

public class ParameterAdder {

    public static void execute(final List<BasicNameValuePair> params, String name, String value) {
        if (Objects.isNull(value)) {
            return;
        }

        params.add(new BasicNameValuePair(name, value));
    }
}
