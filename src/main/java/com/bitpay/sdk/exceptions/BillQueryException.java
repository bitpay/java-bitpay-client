package com.bitpay.sdk.exceptions;

public class BillQueryException extends BillException {
    /**
     * Construct the BillQueryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public BillQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve bill";
        String BitPayCode = "BITPAY-BILL-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}