package com.bitpay.sdk.exceptions;

public class SubscriptionUpdateException extends SubscriptionException {
    /**
     * Construct the SubscriptionUpdateException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public SubscriptionUpdateException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to update subscription";
        String BitPayCode = "BITPAY-SUBSCRIPTION-UPDATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}