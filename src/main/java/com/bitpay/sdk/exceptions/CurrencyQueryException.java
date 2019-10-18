package com.bitpay.sdk.exceptions;

public class CurrencyQueryException extends CurrencyException {
    /**
     * Construct the CurrencyQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public CurrencyQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve currencies";
        String BitPayCode = "BITPAY-CURRENCY-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}