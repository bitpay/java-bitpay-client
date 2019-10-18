package com.bitpay.sdk.exceptions;

public class PayoutCreationException extends BitPayException {
    /**
     * Construct the PayoutCreationException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutCreationException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create payout batch";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-SUBMIT";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}