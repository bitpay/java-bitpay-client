package com.bitpay.sdk.exceptions;

public class RefundCreationException extends RefundException {
    /**
     * Construct the RefundCreationException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public RefundCreationException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create refund";
        String BitPayCode = "BITPAY-REFUND-CREATE";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}