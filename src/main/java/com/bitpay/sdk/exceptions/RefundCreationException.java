package com.bitpay.sdk.exceptions;

public class RefundCreationException extends RefundException {
    /**
     * Construct the RefundCreationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public RefundCreationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create refund";
        String BitPayCode = "BITPAY-REFUND-CREATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}