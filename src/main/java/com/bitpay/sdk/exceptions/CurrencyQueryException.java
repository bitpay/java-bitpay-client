package com.bitpay.sdk.exceptions;

public class CurrencyQueryException extends CurrencyException {
    /**
     * Construct the CurrencyQueryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public CurrencyQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve currencies";
        String BitPayCode = "BITPAY-CURRENCY-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}