package com.bitpay.sdk.exceptions;

public class CurrencyException extends BitPayException {
    /**
     * Construct the CurrencyException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public CurrencyException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the currencies";
        String BitPayCode = "BITPAY-CURRENCY-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}