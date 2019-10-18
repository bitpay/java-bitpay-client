package com.bitpay.sdk.exceptions;

public class RefundException extends BitPayException {
    /**
     * Construct the RefundException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public RefundException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the refund";
        String BitPayCode = "BITPAY-REFUND-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}