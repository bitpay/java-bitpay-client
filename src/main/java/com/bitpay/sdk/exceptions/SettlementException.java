/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Settlement.

 * @see com.bitpay.sdk.exceptions.SettlementQueryException
 *
 * @link https://bitpay.com/api/#rest-api-error-codes
 */
public class SettlementException extends BitPayException {
    /**
     * Construct the SettlementException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public SettlementException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "An unexpected error occurred while trying to manage the settlements";
        String BitPayCode = "BITPAY-SETTLEMENTS-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }
}