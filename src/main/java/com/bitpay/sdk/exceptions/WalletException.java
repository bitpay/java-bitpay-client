package com.bitpay.sdk.exceptions;

public class WalletException extends BitPayException {
    /**
     * Construct the WalletException.
     *
     * @param message String [optional] The Exception message to throw.
     */
    public WalletException(String message) {
        super(BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage wallets";
        String BitPayCode = "BITPAY-WALLET-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + "-> " + message;
        }

        return message;
    }
}