package com.bitpay.sdk.exceptions;

public class PayoutBatchCreationException extends PayoutBatchException {
    /**
     * Construct the PayoutBatchCreationException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutBatchCreationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create payout batch.";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-CREATE";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}