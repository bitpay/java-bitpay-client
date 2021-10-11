package com.bitpay.sdk.exceptions;

public class InvoiceCreationException extends InvoiceException {
    /**
     * Construct the InvoiceCreationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceCreationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create invoice";
        String BitPayCode = "BITPAY-INVOICE-CREATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}