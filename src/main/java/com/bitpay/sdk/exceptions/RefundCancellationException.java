package com.bitpay.sdk.exceptions;

public class RefundCancellationException extends RefundException {
    /**
     * Construct the RefundCancellationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public RefundCancellationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to cancel refund";
        String BitPayCode = "BITPAY-REFUND-CANCEL";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}