package com.bitpay.sdk.exceptions;

public class InvoiceQueryException extends InvoiceException {
    /**
     * Construct the InvoiceQueryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve invoice";
        String BitPayCode = "BITPAY-INVOICE-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}