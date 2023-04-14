/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * <p>
 * Exception thrown for a POST when the Refund cannot be created.
 * </p>
 * <ul>
 *   <li>First two digits: HTTP method</li>
 *   <li>Second two digits: Resource</li>
 *   <li>Final two digits: Error</li>
 * </ul>
 * <p>HTTP method digits for this class: 01</p>
 * <p>Resource digits for the this class: 02</p>
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
 * eg 010201
 * </pre>
 *
 * @see <a href="https://bitpay.readme.io/reference/error-codes">Rest API Error Codes</a>
 */
public class RefundCreationException extends RefundException {
    /**
     * Construct the RefundCreationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public RefundCreationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create refund";
        String BitPayCode = "BITPAY-REFUND-CREATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}