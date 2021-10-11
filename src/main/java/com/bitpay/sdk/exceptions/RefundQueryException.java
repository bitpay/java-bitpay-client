package com.bitpay.sdk.exceptions;

public class RefundQueryException extends RefundException {
    /**
     * Construct the RefundQueryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public RefundQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve refund";
        String BitPayCode = "BITPAY-REFUND-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}