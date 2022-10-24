/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.exceptions;

/**
 * <p>
 * Exception thrown for a POST when the Invoice cannot be created.
 * </p>
 * <ul>
 *   <li>First two digits: HTTP method</li>
 *   <li>Second two digits: Resource</li>
 *   <li>Final two digits: Error</li>
 * </ul>
 * <p>HTTP method digits for this class: 01</p>
 * <p>Resource digits for the Invoice: 01</p>
 * <p>Error digits for the Invoice:</p>
 * <ul>
 *     <li>00 - Generic server error</li>
 *     <li>01 - Resource not found</li>
 *     <li>02 - Invalid parameters</li>
 *     <li>03 - Missing parameters</li>
 *     <li>08 - Invoice is missing email or SMS</li>
 *     <li>09 - SMS not verified</li>
 *     <li>10 - Invoice price is below minimum threshold</li>
 *     <li>11 - Invoice price is above maximum threshold</li>
 *     <li>12 - Invalid SMS number</li>
 *     <li>13 - Error verifying SMS</li>
 *     <li>14 - Unable to update contact information on high value transaction</li>
 *     <li>15 - Email already set on invoice</li>
 *     <li>16 - Unable to perform action outside of demo environment</li>
 *     <li>17 - Invalid invoice state</li>
 *     <li>18 - Misconfigured account</li>
 * </ul>
 * <pre>
 * eg 010101
 * </pre>
 *
 * @see <a href="https://bitpay.com/api/#rest-api-error-codes">Rest API Error Codes</a>
 */
public class InvoiceCreationException extends InvoiceException {
    /**
     * Construct the InvoiceCreationException.
     *
     * @param status String [optional] The Exception code to throw.
     * @param message String [optional] The Exception message to throw.
     */
    public InvoiceCreationException(String status, String message) {
        super(status, BuildMessage(message));
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Failed to create invoice";
        String BitPayCode = "BITPAY-INVOICE-CREATE";

        message = BitPayCode + ": " + BitPayMessage + " -> " + message;

        return message;
    }
}