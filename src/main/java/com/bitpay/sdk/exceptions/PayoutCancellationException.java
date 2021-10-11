package com.bitpay.sdk.exceptions;

public class PayoutCancellationException extends PayoutException {
    /**
     * Construct the PayoutCancellationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutCancellationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to cancel payout batch";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-CANCEL";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}