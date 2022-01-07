package com.bitpay.sdk.exceptions;

public class PayoutRecipientNotificationException extends PayoutRecipientException {
    /**
     * Construct the PayoutRecipientNotificationException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutRecipientNotificationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to send payout recipient notification.";
        String BitPayCode = "BITPAY-PAYOUT-RECIPIENT-NOTIFICATION";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}
