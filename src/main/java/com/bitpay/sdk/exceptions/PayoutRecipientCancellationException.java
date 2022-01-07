package com.bitpay.sdk.exceptions;

public class PayoutRecipientCancellationException extends PayoutRecipientException {
    /**
     * Construct the PayoutRecipientCancellationException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutRecipientCancellationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to cancel payout recipient";
        String BitPayCode = "BITPAY-PAYOUT-RECIPIENT-CANCELLATION";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}
