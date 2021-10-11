package com.bitpay.sdk.exceptions;

public class SubscriptionCreationException extends SubscriptionException {
    /**
     * Construct the SubscriptionCreationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public SubscriptionCreationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create subscription";
        String BitPayCode = "BITPAY-SUBSCRIPTION-CREATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}