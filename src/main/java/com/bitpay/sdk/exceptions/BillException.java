package com.bitpay.sdk.exceptions;

public class BillException extends BitPayException {
    /**
     * Construct the BillException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public BillException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the bill";
        String BitPayCode = "BITPAY-BILL-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}