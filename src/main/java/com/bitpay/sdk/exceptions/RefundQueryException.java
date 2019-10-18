package com.bitpay.sdk.exceptions;

public class RefundQueryException extends RefundException {
    /**
     * Construct the RefundQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public RefundQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve refund";
        String BitPayCode = "BITPAY-REFUND-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}