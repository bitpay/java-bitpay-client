package com.bitpay.sdk.exceptions;

public class PayoutRecipientCreationException extends PayoutRecipientException {
    /**
     * Construct the PayoutRecipientCreationException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutRecipientCreationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to submit payout recipient.";
        String BitPayCode = "BITPAY-PAYOUT-RECIPIENT-CREATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}
