/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * <p>
 * Exception thrown for a PUT when the Refund cannot be updated.
 * </p>
 * <ul>
 *   <li>First two digits: HTTP method</li>
 *   <li>Second two digits: Resource</li>
 *   <li>Final two digits: Error</li>
 * </ul>
 * <p>HTTP method digits for this class: 02</p>
 * <p>Resource digits for the this class: 03</p>
 * <p>Error digits for this class:</p>
 * <ul>
 *     <li>00 - Generic server error</li>
 *     <li>01 - Refund not found</li>
 *     <li>02 - Invalid parameters</li>
 *     <li>03 - Missing parameters</li>
 *     <li>04 - Active refund request already exists</li>
 *     <li>07 - Invalid invoice state for refund</li>
 *     <li>08 - Fees are greater than refund amount</li>
 * </ul>
 * <pre>
 * eg 020301
 * </pre>
 *
 * @see <a href="https://bitpay.readme.io/reference/error-codes">Rest API Error Codes</a>
 */
public class RefundUpdateException extends RefundException {
    /**
     * Construct the RefundUpdateException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public RefundUpdateException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to update refund";
        String BitPayCode = "BITPAY-REFUND-UPDATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}