package com.bitpay.sdk.exceptions;

public class BillUpdateException extends BillException {
    /**
     * Construct the BillUpdateException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public BillUpdateException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to update bill";
        String BitPayCode = "BITPAY-BILL-UPDATE";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}