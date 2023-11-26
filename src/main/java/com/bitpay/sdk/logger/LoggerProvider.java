/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.logger;

import java.util.Objects;

public class LoggerProvider {

    private static BitPayLogger logger = null;

    private LoggerProvider() {
    }

    public static BitPayLogger getLogger() {
        if (Objects.isNull(logger)) {
            logger = new EmptyLogger();
        }

        return logger;
    }

    /**
     * Set BitPayLogger.
     *
     * @param bitPayLogger BitPayLogger
     */
    public static void setLogger(BitPayLogger bitPayLogger) {
        logger = bitPayLogger;
    }
}
