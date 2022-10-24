/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Refund.
 *
 * @see com.bitpay.sdk.exceptions.RefundCancellationException
 * @see com.bitpay.sdk.exceptions.RefundCreationException
 * @see com.bitpay.sdk.exceptions.RefundUpdateException
 * @see com.bitpay.sdk.exceptions.RefundQueryException
 *
 * @see <a href="https://bitpay.com/api/#rest-api-error-codes">Rest API Error Codes</a>
 */
public class RefundException extends BitPayException {
    /**
     * Construct the RefundException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public RefundException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the refund";
        String BitPayCode = "BITPAY-REFUND-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}