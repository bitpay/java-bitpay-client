package com.bitpay.sdk.exceptions;

public class SubscriptionCreationException extends SubscriptionException {
    /**
     * Construct the SubscriptionCreationException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public SubscriptionCreationException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create subscription";
        String BitPayCode = "BITPAY-SUBSCRIPTION-CREATE";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}