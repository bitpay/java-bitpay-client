package com.bitpay.sdk.exceptions;

public class SettlementQueryException extends SettlementException {
    /**
     * Construct the SettlementQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public SettlementQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve settlements";
        String BitPayCode = "BITPAY-SETTLEMENTS-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}