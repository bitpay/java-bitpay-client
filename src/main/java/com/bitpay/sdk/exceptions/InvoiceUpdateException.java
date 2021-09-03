package com.bitpay.sdk.exceptions;

public class InvoiceUpdateException extends InvoiceException {
    /**
     * Construct the InvoiceUpdateException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceUpdateException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to update invoice";
        String BitPayCode = "BITPAY-INVOICE-UPDATE";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}