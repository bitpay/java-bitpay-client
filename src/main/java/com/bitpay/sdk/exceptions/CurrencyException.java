/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Currency.
 *
 * @see com.bitpay.sdk.exceptions.CurrencyQueryException
 *
 * @link https://bitpay.com/api/#rest-api-error-codes
 */
public class CurrencyException extends BitPayException {
    /**
     * Construct the CurrencyException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public CurrencyException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the currencies";
        String BitPayCode = "BITPAY-CURRENCY-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}