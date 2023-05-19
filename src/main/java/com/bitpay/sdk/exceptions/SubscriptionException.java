/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Refund.
 *
 * @see com.bitpay.sdk.exceptions.SubscriptionCreationException
 * @see com.bitpay.sdk.exceptions.SubscriptionUpdateException
 * @see com.bitpay.sdk.exceptions.SubscriptionQueryException
 *
 * @see <a href="https://bitpay.readme.io/reference/error-codes">Rest API Error Codes</a>
 */
public class SubscriptionException extends BitPayException {
    /**
     * Construct the SubscriptionException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public SubscriptionException(
        String status,
        String message
    ) {
        super(status, buildMessage(message));
    }

    private static String buildMessage(String message) {
        String bitPayMessage = "An unexpected error occurred while trying to manage the subscription";
        String bitPayCode = "BITPAY-SUBSCRIPTION-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = bitPayCode + ": " + bitPayMessage + " -> " + message;
        }

        return message;
    }
}
