package com.bitpay.sdk.exceptions;

public class InvoiceCreationException extends InvoiceException {
    /**
     * Construct the InvoiceCreationException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceCreationException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create invoice";
        String BitPayCode = "BITPAY-INVOICE-CREATE";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}