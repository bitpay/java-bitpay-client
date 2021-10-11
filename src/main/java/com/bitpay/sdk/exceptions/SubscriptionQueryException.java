package com.bitpay.sdk.exceptions;

public class SubscriptionQueryException extends SubscriptionException {
    /**
     * Construct the SubscriptionQueryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public SubscriptionQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve subscription";
        String BitPayCode = "BITPAY-SUBSCRIPTION-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}