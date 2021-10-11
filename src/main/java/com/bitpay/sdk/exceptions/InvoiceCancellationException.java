package com.bitpay.sdk.exceptions;

public class InvoiceCancellationException extends InvoiceException {
    /**
     * Construct the InvoiceCancellationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceCancellationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to cancel invoice";
        String BitPayCode = "BITPAY-INVOICE-CANCEL";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}