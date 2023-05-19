/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Payout Batch.
 *
 * @see com.bitpay.sdk.exceptions.PayoutCancellationException
 * @see com.bitpay.sdk.exceptions.PayoutCreationException
 * @see com.bitpay.sdk.exceptions.PayoutNotificationException
 * @see com.bitpay.sdk.exceptions.PayoutQueryException
 *
 * @see <a href="https://bitpay.readme.io/reference/error-codes">Rest API Error Codes</a>
 */
public class PayoutException extends BitPayException {
    /**
     * Construct the PayoutException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutException(
        String status,
        String message
    ) {
        super(status, buildMessage(message));
    }

    private static String buildMessage(String message) {
        String bitPayMessage = "An unexpected error occurred while trying to manage the payout.";
        String bitPayCode = "BITPAY-PAYOUT-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = bitPayCode + ": " + bitPayMessage + " -> " + message;
        }

        return message;
    }
}
