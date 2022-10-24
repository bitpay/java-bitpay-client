/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * <p>
 * Exception thrown for a POST when the Payout Batch cannot be retrieved.
 * </p>
 * <ul>
 *   <li>First two digits: HTTP method</li>
 *   <li>Second two digits: Resource</li>
 *   <li>Final two digits: Error</li>
 * </ul>
 * <p>HTTP method digits for this class: 02</p>
 * <p>Resource digits for the Payout Batch: 00</p>
 * <p>Error digits for the Payout Batch:</p>
 * <ul>
 *     <li>00 - Generic server error</li>
 *     <li>01 - Resource not found</li>
 *     <li>02 - Invalid parameters</li>
 *     <li>03 - Missing parameters</li>
 * </ul>
 * <pre>
 * eg 020001
 * </pre>
 *
 * @see <a href="https://bitpay.com/api/#rest-api-error-codes">Rest API Error Codes</a>
 */
public class PayoutBatchQueryException extends PayoutBatchException {
    /**
     * Construct the PayoutBatchQueryException.
     *
     * @param status  String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public PayoutBatchQueryException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to retrieve payout batch.";
        String BitPayCode = "BITPAY-PAYOUT-BATCH-GET";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}