package com.bitpay.sdk;

public class BitPayException extends Exception {

    private static final long serialVersionUID = 1L;

    public BitPayException(String message) {
        super(message);
    }
}
