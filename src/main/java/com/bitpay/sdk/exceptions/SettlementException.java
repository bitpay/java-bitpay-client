package com.bitpay.sdk.exceptions;

public class SettlementException extends BitPayException {
    /**
     * Construct the SettlementException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public SettlementException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the settlements";
        String BitPayCode = "BITPAY-SETTLEMENTS-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}