/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Invoice.
 *
 * @see com.bitpay.sdk.exceptions.InvoiceCancellationException
 * @see com.bitpay.sdk.exceptions.InvoiceCreationException
 * @see com.bitpay.sdk.exceptions.InvoiceQueryException
 * @see com.bitpay.sdk.exceptions.InvoiceUpdateException
 *
 * @see <a href="https://bitpay.readme.io/reference/error-codes">Rest API Error Codes</a>
 */
public class InvoiceException extends BitPayException {
    /**
     * Construct the InvoiceException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the invoice";
        String BitPayCode = "BITPAY-INVOICE-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}