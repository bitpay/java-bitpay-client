package com.bitpay.sdk.exceptions;

public class PayoutCreationException extends BitPayException {
    /**
     * Construct the PayoutCreationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutCreationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create payout batch";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-SUBMIT";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}