/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.model;

public interface ModelConfiguration {

    /**
     * Used for json mapping. We want to give possibility to change specific field for null (eg. update model)
     * together with @JsonInclude(JsonInclude.Include.NON_DEFAULT)
     */
    String DEFAULT_NON_SENT_VALUE = "";
}
