package com.bitpay.sdk.exceptions;

public class LedgerException extends BitPayException {
    /**
     * Construct the LedgerException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public LedgerException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the ledger";
        String BitPayCode = "BITPAY-LEDGER-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}