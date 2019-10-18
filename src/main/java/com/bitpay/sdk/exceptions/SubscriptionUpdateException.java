package com.bitpay.sdk.exceptions;

public class SubscriptionUpdateException extends SubscriptionException {
    /**
     * Construct the SubscriptionUpdateException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public SubscriptionUpdateException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to update subscription";
        String BitPayCode = "BITPAY-SUBSCRIPTION-UPDATE";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}