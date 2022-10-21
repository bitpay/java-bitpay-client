/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Refund.
 *
 * @see com.bitpay.sdk.exceptions.SubscriptionCreationException
 * @see com.bitpay.sdk.exceptions.SubscriptionUpdateException
 * @see com.bitpay.sdk.exceptions.SubscriptionQueryException
 *
 * @link https://bitpay.com/api/#rest-api-error-codes
 */
public class SubscriptionException extends BitPayException {
    /**
     * Construct the SubscriptionException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public SubscriptionException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the subscription";
        String BitPayCode = "BITPAY-SUBSCRIPTION-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}