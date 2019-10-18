package com.bitpay.sdk.exceptions;

public class RateQueryException extends RateException {
    /**
     * Construct the RateQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public RateQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve rates";
        String BitPayCode = "BITPAY-RATES-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}