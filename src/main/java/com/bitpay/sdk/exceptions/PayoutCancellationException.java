package com.bitpay.sdk.exceptions;

public class PayoutCancellationException extends PayoutException {
    /**
     * Construct the PayoutCancellationException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutCancellationException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to cancel payout batch";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-CANCEL";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}