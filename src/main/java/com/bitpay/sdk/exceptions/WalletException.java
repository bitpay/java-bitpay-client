/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Wallet.
 *
 * @see com.bitpay.sdk.exceptions.WalletQueryException
 *
 * @see <a href="https://bitpay.readme.io/reference/error-codes">Rest API Error Codes</a>
 */
public class WalletException extends BitPayException {
    /**
     * Construct the WalletException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public WalletException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage wallets";
        String BitPayCode = "BITPAY-WALLET-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}