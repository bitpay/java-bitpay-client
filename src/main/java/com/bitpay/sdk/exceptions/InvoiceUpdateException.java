package com.bitpay.sdk.exceptions;

public class InvoiceUpdateException extends InvoiceException {
    /**
     * Construct the InvoiceUpdateException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceUpdateException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to update invoice";
        String BitPayCode = "BITPAY-INVOICE-UPDATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}