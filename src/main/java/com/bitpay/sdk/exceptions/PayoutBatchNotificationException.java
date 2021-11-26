package com.bitpay.sdk.exceptions;

public class PayoutBatchNotificationException extends PayoutBatchException {
    /**
     * Construct the PayoutBatchNotificationException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutBatchNotificationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to send payout batch notification.";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-NOTIFICATION";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}
