package com.bitpay.sdk.exceptions;

public class PayoutNotificationException extends PayoutException {
    /**
     * Construct the PayoutNotificationException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutNotificationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to send payout notification.";
        String BitPayCode = "BITPAY-PAYOUT-NOTIFICATION";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}
