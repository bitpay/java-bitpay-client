package com.bitpay.sdk.exceptions;

public class PayoutRecipientUpdateException extends PayoutRecipientException {
    /**
     * Construct the PayoutRecipientUpdateException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutRecipientUpdateException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to update payout recipient.";
        String BitPayCode = "BITPAY-PAYOUT-RECIPIENT-UPDATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}
