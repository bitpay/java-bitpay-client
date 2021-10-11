package com.bitpay.sdk.exceptions;

public class WalletQueryException extends WalletException {
    /**
     * Construct the WalletQueryException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public WalletQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve supported wallets";
        String BitPayCode = "BITPAY-WALLET-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}