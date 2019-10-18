package com.bitpay.sdk.exceptions;

public class BillCreationException extends BillException {
    /**
     * Construct the BillCreationException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public BillCreationException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create bill";
        String BitPayCode = "BITPAY-BILL-CREATE";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}