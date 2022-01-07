package com.bitpay.sdk.exceptions;

public class PayoutBatchCancellationException extends PayoutBatchException {
    /**
     * Construct the PayoutBatchCancellationException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutBatchCancellationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to cancel payout batch.";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-CANCELLATION";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}
