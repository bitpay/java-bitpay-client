/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Ledger.
 *
 * @see com.bitpay.sdk.exceptions.LedgerQueryException
 *
 * @link https://bitpay.com/api/#rest-api-error-codes
 */
public class LedgerException extends BitPayException {
    /**
     * Construct the LedgerException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public LedgerException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the ledger";
        String BitPayCode = "BITPAY-LEDGER-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}