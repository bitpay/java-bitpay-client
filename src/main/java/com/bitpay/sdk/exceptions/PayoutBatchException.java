package com.bitpay.sdk.exceptions;

public class PayoutBatchException extends BitPayException {
    /**
     * Construct the PayoutBatchException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutBatchException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occured while trying to manage the payout batch.";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}