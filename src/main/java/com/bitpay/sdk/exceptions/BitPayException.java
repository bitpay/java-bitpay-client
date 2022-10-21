/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.exceptions;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.TextUtils;

/**
 * General BitPay Exception which is inherited by all other exceptions.
 *
 * @link https://bitpay.com/api/#rest-api-error-codes
 */
public class BitPayException extends ClientProtocolException {
    private static final long serialVersionUID = -7186627969477257933L;
    private final String statusCode;
    private final String reasonPhrase;
    /**
     * Construct the BitPayException.
     *
     * @param statusCode String [optional] The Exception code to throw.
     * @param reasonPhrase String [optional] The Exception message to throw.
     */
    public BitPayException(String statusCode, String reasonPhrase) {
        super(String.format("Status: %s" + (TextUtils.isBlank(reasonPhrase) ? "" : " -> Reason: %s"), BuildStatus(statusCode), BuildMessage(reasonPhrase)));
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    private static String BuildMessage(String message) {
        String BitPayMessage = "Unexpected Bitpay exeption.";
        String BitPayCode = "BITPAY-GENERIC";

        if (message.isEmpty() || !message.contains("BITPAY-")) {
            message = BitPayCode + ": " + BitPayMessage + " -> " + message;
        }

        return message;
    }

    private static String BuildStatus(String status) {
        if (status == null) {
            status = "000000";
        }

        return status;
    }

    /**
     * <p>An error code consists of 6 digits. </p>
     * <p>The first two digits of an error code represent the HTTP method that was used to call it.</p>
     * <p>The next two digits refer to the resource that was impacted.</p>
     * <p>The last two digits refer to the specific error.</p>
     * <p>eg. 010103 - Missing parameters for Invoice POST request.</p>
     * @return String
     */
    public String getStatusCode() {
        return this.statusCode;
    }

    /**
     * <p>Reason phrase including BitPay Code and BitPay Message.</p>
     * <p>eg. BITPAY-BILL-UPDATE: Failed to update bill -> failed to deserialize BitPay server response (Bill) </p>
     * @return String
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
