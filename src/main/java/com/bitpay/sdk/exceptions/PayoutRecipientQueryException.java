package com.bitpay.sdk.exceptions;

public class PayoutRecipientQueryException extends PayoutRecipientException {
    /**
     * Construct the PayoutRecipientQueryException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutRecipientQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve payout recipient.";
        String BitPayCode = "BITPAY-PAYOUT-RECIPIENT-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}
