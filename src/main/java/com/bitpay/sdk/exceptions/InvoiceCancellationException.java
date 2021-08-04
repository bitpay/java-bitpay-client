package com.bitpay.sdk.exceptions;

public class InvoiceCancellationException extends InvoiceException {
    /**
     * Construct the InvoiceCancellationException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceCancellationException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to cancel invoice";
        String BitPayCode = "BITPAY-INVOICE-CANCEL";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}