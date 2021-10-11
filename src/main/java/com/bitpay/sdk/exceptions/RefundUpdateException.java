package com.bitpay.sdk.exceptions;

public class RefundUpdateException extends RefundException {
    /**
     * Construct the RefundUpdateException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public RefundUpdateException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to update refund";
        String BitPayCode = "BITPAY-REFUND-UPDATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}