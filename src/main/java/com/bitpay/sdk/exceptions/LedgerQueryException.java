package com.bitpay.sdk.exceptions;

public class LedgerQueryException extends LedgerException {
    /**
     * Construct the LedgerQueryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public LedgerQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve ledger";
        String BitPayCode = "BITPAY-LEDGER-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}