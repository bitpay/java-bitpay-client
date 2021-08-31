package com.bitpay.sdk.exceptions;

public class WalletQueryException extends RefundException {
    /**
     * Construct the WalletQueryException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public WalletQueryException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve supported wallets";
        String BitPayCode = "BITPAY-WALLET-GET";

        message = BitPayCode + ": " + BitPayMessage + "-> " + message;

        return message;
    }
}