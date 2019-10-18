package com.bitpay.sdk.exceptions;

public class InvoiceQueryException extends InvoiceException {
    /**
     * Construct the InvoiceQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve invoice";
        String BitPayCode = "BITPAY-INVOICE-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}