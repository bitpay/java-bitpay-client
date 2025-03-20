/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.invoice.Refund;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals("ee26b5e0-9185-493e-bc12-e846d5fcf07c", result.getGuid());
        Assertions.assertEquals(10, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.getInvoice());
        Assertions.assertNull(result.getPreview());
        Assertions.assertEquals(false, result.getImmediate());
        Assertions.assertEquals(false, result.getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund", result.getReference());
        Assertions.assertEquals(0.04, result.getRefundFee());
        Assertions.assertEquals("2021-08-29T20:45:35.368Z", result.getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000594), result.getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000020), result.getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.getId());
        Assertions.assertEquals("2021-08-29T20:45:34Z", result.getRequestDate().toString());
        Assertions.assertEquals("created", result.getStatus());
    }

    @Test
    public void it_should_get_refund_by_id() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/refunds/WoE46gSLkJQS48RJEiNw3L?token=someMerchantToken",
            "GET",
            null,
            getPreparedJsonDataFromFile("getRefundResponse.json")
        );

        // when
        Refund result = this.getTestedClass().getById("WoE46gSLkJQS48RJEiNw3L");

        // then
        Assertions.assertEquals(EXAMPLE_UUID, result.getGuid());
        Assertions.assertEquals(10, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.getInvoice());
        Assertions.assertNull(result.getPreview());
        Assertions.assertEquals(false, result.getImmediate());
        Assertions.assertEquals(false, result.getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund", result.getReference());
        Assertions.assertEquals(0.04, result.getRefundFee());
        Assertions.assertEquals("2021-08-29T20:45:35.368Z", result.getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000594), result.getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000020), result.getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.getId());
        Assertions.assertEquals("2021-08-29T20:45:34Z", result.getRequestDate().toString());
        Assertions.assertEquals("created", result.getStatus());
    }

    @Test
    public void it_should_get_refund_by_guid() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/refunds/guid/ee26b5e0-9185-493e-bc12-e846d5fcf07c?token=someMerchantToken",
            "GET",
            null,
            getPreparedJsonDataFromFile("getRefundResponse.json")
        );

        // when
        Refund result = this.getTestedClass().getByGuid(EXAMPLE_UUID);

        // then
        Assertions.assertEquals(EXAMPLE_UUID, result.getGuid());
        Assertions.assertEquals(10, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.getInvoice());
        Assertions.assertNull(result.getPreview());
        Assertions.assertEquals(false, result.getImmediate());
        Assertions.assertEquals(false, result.getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund", result.getReference());
        Assertions.assertEquals(0.04, result.getRefundFee());
        Assertions.assertEquals("2021-08-29T20:45:35.368Z", result.getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000594), result.getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000020), result.getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.getId());
        Assertions.assertEquals("2021-08-29T20:45:34Z", result.getRequestDate().toString());
        Assertions.assertEquals("created", result.getStatus());
    }

    @Test
    public void it_should_get_refunds_by_invoice_id() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/refunds?token=someMerchantToken&invoiceId=Hpqc63wvE1ZjzeeH4kEycF",
            "GET",
            null,
            getPreparedJsonDataFromFile("getRefundsByInvoiceId.json")
        );

        // when
        List<Refund> result = this.getTestedClass().getRefundsByInvoiceId("Hpqc63wvE1ZjzeeH4kEycF");

        // then
        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(5.0, result.get(0).getAmount());
        Assertions.assertEquals("USD", result.get(0).getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.get(0).getInvoice());
        Assertions.assertNull(result.get(0).getPreview());
        Assertions.assertEquals(false, result.get(0).getImmediate());
        Assertions.assertEquals(false, result.get(0).getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund", result.get(0).getReference());
        Assertions.assertEquals(0.02, result.get(0).getRefundFee());
        Assertions.assertEquals("2021-08-28T22:49:33.368Z", result.get(0).getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000297), result.get(0).getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000010), result.get(0).getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.get(0).getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.get(0).getId());
        Assertions.assertEquals("2021-08-28T22:49:33Z", result.get(0).getRequestDate().toString());
        Assertions.assertEquals("canceled", result.get(0).getStatus());

        Assertions.assertEquals(10, result.get(1).getAmount());
        Assertions.assertEquals("USD", result.get(1).getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.get(1).getInvoice());
        Assertions.assertNull(result.get(1).getPreview());
        Assertions.assertEquals(false, result.get(1).getImmediate());
        Assertions.assertEquals(false, result.get(1).getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund 2", result.get(1).getReference());
        Assertions.assertEquals(0.04, result.get(1).getRefundFee());
        Assertions.assertEquals("2021-08-29T20:45:35.368Z", result.get(1).getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000594), result.get(1).getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000020), result.get(1).getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.get(1).getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.get(1).getId());
        Assertions.assertEquals("2021-08-29T20:45:34Z", result.get(1).getRequestDate().toString());
        Assertions.assertEquals("created", result.get(1).getStatus());
    }

    @Test
    public void it_should_update_refund() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/refunds/WoE46gSLkJQS48RJEiNw3L",
            "PUT",
            getPreparedJsonDataFromFile("updateRefundRequest.json"),
            getPreparedJsonDataFromFile("updateRefundResponse.json")
        );

        // when
        Refund result = this.getTestedClass().update("WoE46gSLkJQS48RJEiNw3L", "created");

        // then
        Assertions.assertEquals(10, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.getInvoice());
        Assertions.assertNull(result.getPreview());
        Assertions.assertEquals(false, result.getImmediate());
        Assertions.assertEquals(false, result.getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund", result.getReference());
        Assertions.assertEquals(0.04, result.getRefundFee());
        Assertions.assertEquals("2021-08-29T20:45:35.368Z", result.getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000594), result.getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000020), result.getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.getId());
        Assertions.assertEquals("2021-08-29T20:45:34Z", result.getRequestDate().toString());
        Assertions.assertEquals("created", result.getStatus());
    }

    @Test
    public void it_should_update_refund_by_guid() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/refunds/guid/ee26b5e0-9185-493e-bc12-e846d5fcf07c",
            "PUT",
            getPreparedJsonDataFromFile("updateRefundRequest.json"),
            getPreparedJsonDataFromFile("updateRefundResponse.json")
        );

        // when
        Refund result = this.getTestedClass().updateByGuid("ee26b5e0-9185-493e-bc12-e846d5fcf07c", "created");

        // then
        Assertions.assertEquals(10, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.getInvoice());
        Assertions.assertNull(result.getPreview());
        Assertions.assertEquals(false, result.getImmediate());
        Assertions.assertEquals(false, result.getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund", result.getReference());
        Assertions.assertEquals(0.04, result.getRefundFee());
        Assertions.assertEquals("2021-08-29T20:45:35.368Z", result.getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000594), result.getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000020), result.getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.getId());
        Assertions.assertEquals("2021-08-29T20:45:34Z", result.getRequestDate().toString());
        Assertions.assertEquals("created", result.getStatus());
    }

    @Test
    public void it_should_send_refund_notification() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/refunds/WoE46gSLkJQS48RJEiNw3L/notifications",
            "POST",
            getPreparedJsonDataFromFile("sendRefundNotificationRequest.json"),
            getPreparedJsonDataFromFile("sendRefundNotificationResponse.json")
        );

        // when
        Boolean result = this.getTestedClass().sendRefundNotification("WoE46gSLkJQS48RJEiNw3L", "cM78LHk17Q8fktDE6QLBBFfvH1QKBhRkHibTLcxhgzsu3VDRvSyu3CGi17DuwYxhT");

        // then
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_cancel_refund() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/refunds/WoE46gSLkJQS48RJEiNw3L?token=someMerchantToken",
            "DELETE",
            null,
            getPreparedJsonDataFromFile("cancelRefundResponse.json")
        );

        // when
        Refund result = this.getTestedClass().cancel("WoE46gSLkJQS48RJEiNw3L");

        // then
        Assertions.assertEquals(10, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.getInvoice());
        Assertions.assertNull(result.getPreview());
        Assertions.assertEquals(false, result.getImmediate());
        Assertions.assertEquals(false, result.getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund", result.getReference());
        Assertions.assertEquals(0.04, result.getRefundFee());
        Assertions.assertEquals("2021-08-29T20:45:35.368Z", result.getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000594), result.getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000020), result.getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.getId());
        Assertions.assertEquals("2021-08-29T20:45:34Z", result.getRequestDate().toString());
        Assertions.assertEquals("cancelled", result.getStatus());
    }

    @Test
    public void it_should_cancel_refund_by_guid() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/refunds/guid/WoE46gSLkJQS48RJEiNw3L?token=someMerchantToken",
            "DELETE",
            null,
            getPreparedJsonDataFromFile("cancelRefundResponse.json")
        );

        // when
        Refund result = this.getTestedClass().cancelByGuid("WoE46gSLkJQS48RJEiNw3L");

        // then
        Assertions.assertEquals(10, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Hpqc63wvE1ZjzeeH4kEycF", result.getInvoice());
        Assertions.assertNull(result.getPreview());
        Assertions.assertEquals(false, result.getImmediate());
        Assertions.assertEquals(false, result.getBuyerPaysRefundFee());
        Assertions.assertEquals("Test refund", result.getReference());
        Assertions.assertEquals(0.04, result.getRefundFee());
        Assertions.assertEquals("2021-08-29T20:45:35.368Z", result.getLastRefundNotification().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.000594), result.getTransactionAmount());
        Assertions.assertEquals(BigDecimal.valueOf(0.0000020), result.getTransactionRefundFee());
        Assertions.assertEquals("BTC", result.getTransactionCurrency());
        Assertions.assertEquals("WoE46gSLkJQS48RJEiNw3L", result.getId());
        Assertions.assertEquals("2021-08-29T20:45:34Z", result.getRequestDate().toString());
        Assertions.assertEquals("cancelled", result.getStatus());
    }

    private RefundClient getTestedClass() {
        return RefundClient.getInstance(this.getBitPayClient(), this.getAccessTokens(), this.uuidGenerator);
    }
}
