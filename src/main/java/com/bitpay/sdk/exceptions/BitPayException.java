package com.bitpay.sdk.exceptions;

public class BitPayException extends Exception {
    /**
     * Construct the BitPayException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public BitPayException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Unexpected Bitpay exeption.";
        String BitPayCode = "BITPAY-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}
