/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Invoice.Refund;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RefundClientTest extends AbstractClientTest {

    @Test
    public void it_should_create_refund() throws BitPayException {
        // given
        Mockito.when(this.uuidGenerator.execute()).thenReturn(EXAMPLE_UUID);
        this.addServerJsonResponse(
            "/refunds",
            "POST",
            null,
            getPreparedJsonDataFromFile("createRefundResponse.json")
        );
        Refund refund = new Refund();
        refund.setInvoice("Hpqc63wvE1ZjzeeH4kEycF");
        refund.setAmount(10.00);

        // when
        Refund result = this.getTestedClass().create(refund);

        // then
        String a = "";
    }

    private RefundClient getTestedClass() {
        return new RefundClient(this.getBitPayClient(), this.getAccessTokens(), this.uuidGenerator);
    }
}
