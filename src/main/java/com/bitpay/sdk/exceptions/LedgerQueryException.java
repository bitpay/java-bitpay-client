package com.bitpay.sdk.exceptions;

public class LedgerQueryException extends LedgerException {
    /**
     * Construct the LedgerQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public LedgerQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve ledger";
        String BitPayCode = "BITPAY-LEDGER-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}