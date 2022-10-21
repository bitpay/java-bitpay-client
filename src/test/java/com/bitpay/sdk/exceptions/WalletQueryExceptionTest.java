package com.bitpay.sdk.exceptions;

import org.junit.jupiter.api.Test;

public class WalletQueryExceptionTest  extends AbstractTestException {

    private static final String MESSAGE = "Failed to retrieve supported wallets";
    private static final String CODE = "BITPAY-WALLET-GET";

    @Test
    public void it_should_create_exception_without_status() {
        this.testExceptionWithoutStatus(this.getTestedClass(null), MESSAGE, CODE);
    }

    @Test
    public void it_should_create_exception_with_status() {
        this.testExceptionWithStatus(this.getTestedClass(AbstractTestException.STATUS_CODE), MESSAGE, CODE);
    }

    private WalletQueryException getTestedClass(String status) {
        return new WalletQueryException(status, AbstractTestException.REASON_MESSAGE);
    }
}