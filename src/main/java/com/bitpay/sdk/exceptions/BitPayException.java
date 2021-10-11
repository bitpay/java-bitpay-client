package com.bitpay.sdk.exceptions;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.TextUtils;

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

    public String getStatusCode() {
        return this.statusCode;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
