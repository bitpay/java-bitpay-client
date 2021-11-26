package com.bitpay.sdk.exceptions;

public class PayoutQueryException extends PayoutException {
    /**
     * Construct the PayoutQueryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve payout.";
        String BitPayCode = "BITPAY-PAYOUT-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}