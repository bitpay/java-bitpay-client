package com.bitpay.sdk.exceptions;

public class RefundCancellationException extends RefundException {
    /**
     * Construct the RefundCancellationException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public RefundCancellationException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to cancel refund batch";
        String BitPayCode = "BITPAY-REFUND-CANCEL";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}