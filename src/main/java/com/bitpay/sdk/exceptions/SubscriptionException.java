package com.bitpay.sdk.exceptions;

public class SubscriptionException extends BitPayException {
    /**
     * Construct the SubscriptionException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public SubscriptionException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the subscription";
        String BitPayCode = "BITPAY-SUBSCRIPTION-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}