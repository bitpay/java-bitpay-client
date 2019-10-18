package com.bitpay.sdk.exceptions;

public class PayoutQueryException extends PayoutException {
    /**
     * Construct the PayoutQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve payout batch";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}