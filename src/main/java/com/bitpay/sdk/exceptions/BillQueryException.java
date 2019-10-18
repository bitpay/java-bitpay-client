package com.bitpay.sdk.exceptions;

public class BillQueryException extends BillException {
    /**
     * Construct the BillQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public BillQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve bill";
        String BitPayCode = "BITPAY-BILL-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}