package com.bitpay.sdk.exceptions;

public class RateException extends BitPayException {
    /**
     * Construct the RateException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public RateException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the rates";
        String BitPayCode = "BITPAY-RATES-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}