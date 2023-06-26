/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.exceptions;

/**
 * Exception which is extended by other exceptions related with Payout Batch.
 *
 * @see com.bitpay.sdk.exceptions.PayoutBatchCancellationException
 * @see com.bitpay.sdk.exceptions.PayoutBatchCreationException
 * @see com.bitpay.sdk.exceptions.PayoutBatchNotificationException
 * @see com.bitpay.sdk.exceptions.PayoutBatchQueryException
 *
 * @see <a href="https://bitpay.readme.io/reference/error-codes">Rest API Error Codes</a>
 */
public class PayoutBatchException extends BitPayException {
    /**
     * Construct the PayoutBatchException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutBatchException(
        String status,
        String message
    ) {
        super(status, buildMessage(message));
    }

    private static String buildMessage(String message) {
        String bitPayMessage = "An unexpected error occured while trying to manage the payout batch.";
        String bitPayCode = "BITPAY-PAYOUT-BATCH-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = bitPayCode + ": " + bitPayMessage + " -> " + message;
        }

        return message;
    }
}
