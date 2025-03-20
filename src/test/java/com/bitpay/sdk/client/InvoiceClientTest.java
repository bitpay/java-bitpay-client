/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.invoice.Buyer;
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.model.invoice.InvoiceEventToken;
import com.bitpay.sdk.model.ModelConfiguration;
import com.bitpay.sdk.util.TokenContainer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceClientTest extends AbstractClientTest {

    @Test
    public void it_should_create_invoice() throws BitPayException {
        // given
        Invoice invoice = getInvoiceExample();
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices",
            "POST",
            getPreparedJsonDataFromFile("createInvoiceRequest.json"),
            getPreparedJsonDataFromFile("createInvoiceResponse.json")
        );

        // when
        Invoice result = this.getTestedClass(accessTokens).create(invoice, Facade.MERCHANT, true);

        // then
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("https://bitpay.com/invoice?id=G3viJEJgE8Jk2oekSdgT2A", result.getUrl());
        Assertions.assertEquals("\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"", result.getPosData());
        Assertions.assertEquals("new", result.getStatus());
        Assertions.assertEquals(20, result.getPrice());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("Item 1", result.getItemDesc());
        Assertions.assertEquals("20210511_fghij", result.getOrderId());
        Assertions.assertEquals(1620733980748L, result.getInvoiceTime());
        Assertions.assertEquals(1620734880748L, result.getExpirationTime());
        Assertions.assertEquals(1620733980807L, result.getCurrentTime());
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("G3viJEJgE8Jk2oekSdgT2A", result.getId());
        Assertions.assertFalse(result.getLowFeeDetected());
        Assertions.assertEquals(BigDecimal.ZERO, result.getAmountPaid());
        Assertions.assertEquals("0", result.getDisplayAmountPaid());
        Assertions.assertEquals("false", result.getExceptionStatus());
        Assertions.assertEquals(6, result.getTargetConfirmations());
        Assertions.assertEquals(1, result.getTransactions().size());
        Assertions.assertEquals("2021-05-11T11:54:32.978Z", result.getTransactions().get(0).getReceivedTime().toString());
        Assertions.assertEquals(BigDecimal.valueOf(0.024436520994387978), result.getTransactions().get(0).getExRates().get("WBTC"));
        Assertions.assertEquals(BigDecimal.valueOf(739100), result.getTransactions().get(0).getAmount());
        Assertions.assertEquals("medium", result.getTransactionSpeed());
        Assertions.assertEquals("john@doe.com", result.getBuyer().getEmail());
        Assertions.assertEquals("https://merchantwebsite.com/shop/return", result.getRedirectUrl());
        Assertions.assertFalse(result.getAutoRedirect());
        Assertions.assertEquals("https://merchantwebsite.com/shop/cancel", result.getCloseUrl());
        Assertions.assertEquals(new ArrayList<>(), result.getRefundAddresses());
        Assertions.assertFalse(result.getRefundAddressRequestPending());
        Assertions.assertEquals("john@doe.com", result.getBuyerProvidedEmail());
        Assertions.assertEquals("john@doe.com", result.getInvoiceBuyerProvidedInfo().getEmailAddress());
        Assertions.assertEquals("bitpay", result.getInvoiceBuyerProvidedInfo().getSelectedWallet());
        Assertions.assertEquals("BTC", result.getInvoiceBuyerProvidedInfo().getSelectedTransactionCurrency());
    }

    @Test
    public void it_should_get_invoice() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices/G3viJEJgE8Jk2oekSdgT2A?token=someMerchantToken",
            "GET",
            null,
            getPreparedJsonDataFromFile("getInvoiceResponse.json")
        );

        // when
        Invoice result = this.getTestedClass(accessTokens).get("G3viJEJgE8Jk2oekSdgT2A", Facade.MERCHANT, true);

        // then
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("https://bitpay.com/invoice?id=G3viJEJgE8Jk2oekSdgT2A", result.getUrl());
        Assertions.assertEquals("\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"", result.getPosData());
        Assertions.assertEquals("confirmed", result.getStatus());
        Assertions.assertEquals(20, result.getPrice());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("20210511_fghij", result.getOrderId());
        Assertions.assertEquals(1620733980748L, result.getInvoiceTime());
        Assertions.assertEquals(1620734880748L, result.getExpirationTime());
        Assertions.assertEquals(1620734253073L, result.getCurrentTime());
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("G3viJEJgE8Jk2oekSdgT2A", result.getId());
        Assertions.assertFalse(result.getLowFeeDetected());
        Assertions.assertEquals(BigDecimal.valueOf(739100L), result.getAmountPaid());
        Assertions.assertEquals("0.007391", result.getDisplayAmountPaid());
        Assertions.assertEquals("false", result.getExceptionStatus());
        Assertions.assertEquals(6, result.getTargetConfirmations());
        Assertions.assertEquals(1, result.getTransactions().size());
        Assertions.assertEquals("medium", result.getTransactionSpeed());
        Assertions.assertEquals("john@doe.com", result.getBuyer().getEmail());
        Assertions.assertEquals("https://merchantwebsite.com/shop/return", result.getRedirectUrl());
        Assertions.assertFalse(result.getAutoRedirect());
        Assertions.assertEquals("https://merchantwebsite.com/shop/cancel", result.getCloseUrl());
        Assertions.assertEquals(new ArrayList<>(), result.getRefundAddresses());
        Assertions.assertFalse(result.getRefundAddressRequestPending());
        Assertions.assertEquals("john@doe.com", result.getBuyerProvidedEmail());
        Assertions.assertEquals("john@doe.com", result.getInvoiceBuyerProvidedInfo().getEmailAddress());
        Assertions.assertEquals("bitpay", result.getInvoiceBuyerProvidedInfo().getSelectedWallet());
        Assertions.assertEquals("BCH", result.getInvoiceBuyerProvidedInfo().getSelectedTransactionCurrency());
    }

    @Test
    public void it_should_get_invoice_by_guid() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices/guid/payment#1234?token=someMerchantToken",
            "GET",
            null,
            getPreparedJsonDataFromFile("getInvoiceResponse.json")
        );

        // when
        Invoice result = this.getTestedClass(accessTokens).getByGuid("payment#1234", Facade.MERCHANT, true);

        // then
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("https://bitpay.com/invoice?id=G3viJEJgE8Jk2oekSdgT2A", result.getUrl());
        Assertions.assertEquals("\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"", result.getPosData());
        Assertions.assertEquals("confirmed", result.getStatus());
        Assertions.assertEquals(20, result.getPrice());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("20210511_fghij", result.getOrderId());
        Assertions.assertEquals(1620733980748L, result.getInvoiceTime());
        Assertions.assertEquals(1620734880748L, result.getExpirationTime());
        Assertions.assertEquals(1620734253073L, result.getCurrentTime());
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("G3viJEJgE8Jk2oekSdgT2A", result.getId());
        Assertions.assertFalse(result.getLowFeeDetected());
        Assertions.assertEquals(BigDecimal.valueOf(739100L), result.getAmountPaid());
        Assertions.assertEquals("0.007391", result.getDisplayAmountPaid());
        Assertions.assertEquals("false", result.getExceptionStatus());
        Assertions.assertEquals(6, result.getTargetConfirmations());
        Assertions.assertEquals(1, result.getTransactions().size());
        Assertions.assertEquals("medium", result.getTransactionSpeed());
        Assertions.assertEquals("john@doe.com", result.getBuyer().getEmail());
        Assertions.assertEquals("https://merchantwebsite.com/shop/return", result.getRedirectUrl());
        Assertions.assertFalse(result.getAutoRedirect());
        Assertions.assertEquals("https://merchantwebsite.com/shop/cancel", result.getCloseUrl());
        Assertions.assertEquals(new ArrayList<>(), result.getRefundAddresses());
        Assertions.assertFalse(result.getRefundAddressRequestPending());
        Assertions.assertEquals("john@doe.com", result.getBuyerProvidedEmail());
        Assertions.assertEquals("john@doe.com", result.getInvoiceBuyerProvidedInfo().getEmailAddress());
        Assertions.assertEquals("bitpay", result.getInvoiceBuyerProvidedInfo().getSelectedWallet());
        Assertions.assertEquals("BCH", result.getInvoiceBuyerProvidedInfo().getSelectedTransactionCurrency());
    }

    @Test
    public void it_should_get_invoices() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices?token=someMerchantToken&dateStart=2021-5-10&dateEnd=2021-5-12&status=complete",
            "GET",
            null,
            getPreparedJsonDataFromFile("getInvoicesResponse.json")
        );

        // when
        List<Invoice> result = this.getTestedClass(accessTokens).getInvoices(
            "2021-5-10",
            "2021-5-12",
            "complete",
            null,
            null,
            null
        );

        // then
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("payment#1234", result.get(0).getGuid());
        Assertions.assertEquals("https://bitpay.com/invoice?id=KSnNNfoMDsbRzd1U9ypmVH", result.get(0).getUrl());
        Assertions.assertEquals("\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"", result.get(0).getPosData());
        Assertions.assertEquals("confirmed", result.get(0).getStatus());
        Assertions.assertEquals(20, result.get(0).getPrice());
        Assertions.assertEquals("USD", result.get(0).getCurrency());
        Assertions.assertEquals("20210511_abcde", result.get(0).getOrderId());
        Assertions.assertEquals(1620734545366L, result.get(0).getInvoiceTime());
        Assertions.assertEquals(1620735445366L, result.get(0).getExpirationTime());
        Assertions.assertEquals(1620744196444L, result.get(0).getCurrentTime());
        Assertions.assertEquals("payment#1234", result.get(0).getGuid());
        Assertions.assertEquals("KSnNNfoMDsbRzd1U9ypmVH", result.get(0).getId());
        Assertions.assertFalse(result.get(0).getLowFeeDetected());
        Assertions.assertEquals(BigDecimal.valueOf(744500L), result.get(0).getAmountPaid());
        Assertions.assertEquals("0.007445", result.get(0).getDisplayAmountPaid());
        Assertions.assertEquals("false", result.get(0).getExceptionStatus());
        Assertions.assertEquals(6, result.get(0).getTargetConfirmations());
        Assertions.assertEquals(1, result.get(0).getTransactions().size());
        Assertions.assertEquals("medium", result.get(0).getTransactionSpeed());
        Assertions.assertEquals("john@doe.com", result.get(0).getBuyer().getEmail());
        Assertions.assertEquals("https://merchantwebsite.com/shop/return", result.get(0).getRedirectUrl());
        Assertions.assertFalse(result.get(0).getAutoRedirect());
        Assertions.assertEquals("https://merchantwebsite.com/shop/cancel", result.get(0).getCloseUrl());
        Assertions.assertEquals(new ArrayList<>(), result.get(0).getRefundAddresses());
        Assertions.assertFalse(result.get(0).getRefundAddressRequestPending());
        Assertions.assertEquals("john@doe.com", result.get(0).getBuyerProvidedEmail());
        Assertions.assertEquals("john@doe.com", result.get(0).getInvoiceBuyerProvidedInfo().getEmailAddress());
        Assertions.assertEquals("bitpay", result.get(0).getInvoiceBuyerProvidedInfo().getSelectedWallet());
        Assertions.assertEquals("BCH", result.get(0).getInvoiceBuyerProvidedInfo().getSelectedTransactionCurrency());
    }

    @Test
    public void it_should_update_invoice() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices/G3viJEJgE8Jk2oekSdgT2A",
            "PUT",
            getPreparedJsonDataFromFile("updateInvoiceRequest.json"),
            getPreparedJsonDataFromFile("updateInvoiceResponse.json")
        );

        // when
        Invoice result = this.getTestedClass(accessTokens).update(
    "G3viJEJgE8Jk2oekSdgT2A",
            "+12223334444",
            null,
            null,
            null
        );

        // then
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("+12223334444", result.getBuyerSms());
        Assertions.assertEquals("https://bitpay.com/invoice?id=G3viJEJgE8Jk2oekSdgT2A", result.getUrl());
        Assertions.assertEquals("\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"", result.getPosData());
        Assertions.assertEquals("confirmed", result.getStatus());
        Assertions.assertEquals(20, result.getPrice());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("20210511_fghij", result.getOrderId());
        Assertions.assertEquals(1620733980748L, result.getInvoiceTime());
        Assertions.assertEquals(1620734880748L, result.getExpirationTime());
        Assertions.assertEquals(1620734253073L, result.getCurrentTime());
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("G3viJEJgE8Jk2oekSdgT2A", result.getId());
        Assertions.assertFalse(result.getLowFeeDetected());
        Assertions.assertEquals(BigDecimal.valueOf(739100L), result.getAmountPaid());
        Assertions.assertEquals("0.007391", result.getDisplayAmountPaid());
        Assertions.assertEquals("false", result.getExceptionStatus());
        Assertions.assertEquals(6, result.getTargetConfirmations());
        Assertions.assertEquals(1, result.getTransactions().size());
        Assertions.assertEquals("medium", result.getTransactionSpeed());
        Assertions.assertEquals("john@doe.com", result.getBuyer().getEmail());
        Assertions.assertEquals("https://merchantwebsite.com/shop/return", result.getRedirectUrl());
        Assertions.assertFalse(result.getAutoRedirect());
        Assertions.assertEquals("https://merchantwebsite.com/shop/cancel", result.getCloseUrl());
        Assertions.assertEquals(new ArrayList<>(), result.getRefundAddresses());
        Assertions.assertFalse(result.getRefundAddressRequestPending());
        Assertions.assertEquals("john@doe.com", result.getBuyerProvidedEmail());
        Assertions.assertEquals("john@doe.com", result.getInvoiceBuyerProvidedInfo().getEmailAddress());
        Assertions.assertEquals("bitpay", result.getInvoiceBuyerProvidedInfo().getSelectedWallet());
        Assertions.assertEquals("BCH", result.getInvoiceBuyerProvidedInfo().getSelectedTransactionCurrency());
    }

    @Test
    public void it_should_pay_invoice() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices/pay/G3viJEJgE8Jk2oekSdgT2A",
            "PUT",
            getPreparedJsonDataFromFile("payInvoiceRequest.json"),
            getPreparedJsonDataFromFile("payInvoiceResponse.json")
        );

        // when
        Invoice result = this.getTestedClass(accessTokens).pay("G3viJEJgE8Jk2oekSdgT2A", "complete");

        // then
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("09ebf602-79c0-4abe-abea-d3f7d09de48a", result.getGuid());
        Assertions.assertEquals("AShhrUJ2sEJ4stEzkt5AywcrDDE5A3SpeXsXdbU1TMVo", result.getToken());
        Assertions.assertEquals(12.0, result.getPrice());
        Assertions.assertEquals("medium", result.getTransactionSpeed());
        Assertions.assertNull(result.getFullNotifications());
        Assertions.assertEquals("https://hookb.in/yDGknXr837sGkPZGa6Ed", result.getRedirectUrl());
        Assertions.assertEquals("0e5ab88f-839f-45e1-a5fe-57e01e17af8e", result.getOrderId());
        Assertions.assertEquals("Example", result.getItemDesc());
        Assertions.assertNull(result.getPhysical());
        Assertions.assertNull(result.getPaymentCurrencies());
        Assertions.assertNull(result.getAcceptanceWindow());
        Assertions.assertEquals("Satoshi", result.getBuyer().getName());
        Assertions.assertEquals("District of Columbia", result.getBuyer().getRegion());
        Assertions.assertEquals("merchantName", result.getMerchantName());
        Assertions.assertEquals(ModelConfiguration.DEFAULT_NON_SENT_VALUE, result.getSelectedTransactionCurrency());
        Assertions.assertEquals("https://link.test.bitpay.com/i/AwzYppkN5cDidQJi6g3kRs", result.getUniversalCodes().getBitpay());
        Assertions.assertEquals(0, result.getItemizedDetails().size());
        Assertions.assertTrue(result.getAutoRedirect());
        Assertions.assertEquals("AwzYLpkN1cJidQJi2g1jRs", result.getId());
        Assertions.assertEquals("https://test.bitpay.com/invoice?id=AwzYLpkN1cJidQJi2g1jRs", result.getUrl());
        Assertions.assertEquals("complete", result.getStatus());
        Assertions.assertFalse(result.getLowFeeDetected());
        Assertions.assertEquals(1671107858009L, result.getInvoiceTime());
        Assertions.assertEquals(1671108758009L, result.getExpirationTime());
        Assertions.assertEquals(1671107858346L, result.getCurrentTime());
        Assertions.assertEquals("false", result.getExceptionStatus());
        Assertions.assertEquals(6, result.getTargetConfirmations());
        Assertions.assertEquals(2, result.getTransactions().size());
        Assertions.assertEquals(0, result.getRefundAddresses().size());
        Assertions.assertFalse(result.getRefundAddressRequestPending());
        Assertions.assertEquals("buyer@buyer.com", result.getBuyerProvidedEmail());
        Assertions.assertEquals("Satoshi", result.getInvoiceBuyerProvidedInfo().getName());
        Assertions.assertNull(result.getExtendedNotifications());
        Assertions.assertEquals("BTC", result.getTransactionCurrency());
        Assertions.assertEquals(BigDecimal.valueOf(67900), result.getAmountPaid());
        Assertions.assertEquals("0.000679", result.getDisplayAmountPaid());
        Assertions.assertEquals(11, result.getExchangeRates().size());
        Assertions.assertNull(result.getIsCancelled());
        Assertions.assertFalse(result.getBitpayIdRequired());
        Assertions.assertEquals(11, result.getPaymentSubTotals().size());
        Assertions.assertEquals(11, result.getPaymentTotals().size());
        Assertions.assertEquals(11, result.getPaymentDisplayTotals().size());
        Assertions.assertEquals(11, result.getPaymentDisplaySubTotals().size());
        Assertions.assertNull(result.getNonPayProPaymentReceived());
        Assertions.assertFalse(result.getJsonPayProRequired());
        Assertions.assertEquals(11, result.getPaymentCodes().size());
    }

    @Test
    public void it_should_cancel_invoice() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices/Hpqc63wvE1ZjzeeH4kEycF?token=" + MERCHANT_TOKEN,
            "DELETE",
            null,
            getPreparedJsonDataFromFile("cancelInvoiceSuccessResponse.json")
        );

        // when
        Invoice result = this.getTestedClass(accessTokens).cancel("Hpqc63wvE1ZjzeeH4kEycF");

        // then
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("+12223334444", result.getInvoiceBuyerProvidedInfo().getSms());
        Assertions.assertEquals("https://bitpay.com/invoice?id=G3viJEJgE8Jk2oekSdgT2A", result.getUrl());
        Assertions.assertEquals("\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"", result.getPosData());
        Assertions.assertEquals("expired", result.getStatus());
        Assertions.assertEquals(20, result.getPrice());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("20210511_fghij", result.getOrderId());
        Assertions.assertEquals(1620733980748L, result.getInvoiceTime());
        Assertions.assertEquals(1620734880748L, result.getExpirationTime());
        Assertions.assertEquals(1620734253073L, result.getCurrentTime());
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("G3viJEJgE8Jk2oekSdgT2A", result.getId());
        Assertions.assertFalse(result.getLowFeeDetected());
        Assertions.assertEquals(BigDecimal.valueOf(739100L), result.getAmountPaid());
        Assertions.assertEquals("0.007391", result.getDisplayAmountPaid());
        Assertions.assertEquals("false", result.getExceptionStatus());
        Assertions.assertEquals(6, result.getTargetConfirmations());
        Assertions.assertEquals(0, result.getTransactions().size());
        Assertions.assertEquals("medium", result.getTransactionSpeed());
        Assertions.assertEquals("john@doe.com", result.getBuyer().getEmail());
        Assertions.assertEquals("https://merchantwebsite.com/shop/return", result.getRedirectUrl());
        Assertions.assertFalse(result.getAutoRedirect());
        Assertions.assertEquals("https://merchantwebsite.com/shop/cancel", result.getCloseUrl());
        Assertions.assertEquals(new ArrayList<>(), result.getRefundAddresses());
        Assertions.assertFalse(result.getRefundAddressRequestPending());
        Assertions.assertEquals("john@doe.com", result.getBuyerProvidedEmail());
        Assertions.assertEquals("john@doe.com", result.getInvoiceBuyerProvidedInfo().getEmailAddress());
        Assertions.assertEquals("bitpay", result.getInvoiceBuyerProvidedInfo().getSelectedWallet());
        Assertions.assertEquals("BCH", result.getInvoiceBuyerProvidedInfo().getSelectedTransactionCurrency());
    }

    @Test
    public void it_should_cancel_invoice_by_guid() throws BitPayException {
        // given
        final String guidId = "payment#1234";
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices/guid/" + guidId + "?token=" + MERCHANT_TOKEN,
            "DELETE",
            null,
            getPreparedJsonDataFromFile("cancelInvoiceSuccessResponse.json")
        );

        // when
        Invoice result = this.getTestedClass(accessTokens).cancelByGuid("payment#1234", false);

        // then
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("+12223334444", result.getInvoiceBuyerProvidedInfo().getSms());
        Assertions.assertEquals("https://bitpay.com/invoice?id=G3viJEJgE8Jk2oekSdgT2A", result.getUrl());
        Assertions.assertEquals("\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"", result.getPosData());
        Assertions.assertEquals("expired", result.getStatus());
        Assertions.assertEquals(20, result.getPrice());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("20210511_fghij", result.getOrderId());
        Assertions.assertEquals(1620733980748L, result.getInvoiceTime());
        Assertions.assertEquals(1620734880748L, result.getExpirationTime());
        Assertions.assertEquals(1620734253073L, result.getCurrentTime());
        Assertions.assertEquals("payment#1234", result.getGuid());
        Assertions.assertEquals("G3viJEJgE8Jk2oekSdgT2A", result.getId());
        Assertions.assertFalse(result.getLowFeeDetected());
        Assertions.assertEquals(BigDecimal.valueOf(739100L), result.getAmountPaid());
        Assertions.assertEquals("0.007391", result.getDisplayAmountPaid());
        Assertions.assertEquals("false", result.getExceptionStatus());
        Assertions.assertEquals(6, result.getTargetConfirmations());
        Assertions.assertEquals(0, result.getTransactions().size());
        Assertions.assertEquals("medium", result.getTransactionSpeed());
        Assertions.assertEquals("john@doe.com", result.getBuyer().getEmail());
        Assertions.assertEquals("https://merchantwebsite.com/shop/return", result.getRedirectUrl());
        Assertions.assertFalse(result.getAutoRedirect());
        Assertions.assertEquals("https://merchantwebsite.com/shop/cancel", result.getCloseUrl());
        Assertions.assertEquals(new ArrayList<>(), result.getRefundAddresses());
        Assertions.assertFalse(result.getRefundAddressRequestPending());
        Assertions.assertEquals("john@doe.com", result.getBuyerProvidedEmail());
        Assertions.assertEquals("john@doe.com", result.getInvoiceBuyerProvidedInfo().getEmailAddress());
        Assertions.assertEquals("bitpay", result.getInvoiceBuyerProvidedInfo().getSelectedWallet());
        Assertions.assertEquals("BCH", result.getInvoiceBuyerProvidedInfo().getSelectedTransactionCurrency());
    }

    @Test
    public void it_should_request_invoice_webhook_to_be_resent() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices/Hpqc63wvE1ZjzeeH4kEycF/notifications",
            "POST",
            "{\"token\":\"cM78LHk17Q8fktDE6QLBBFfvH1QKBhRkHibTLcxhgzsu3VDRvSyu3CGi17DuwYxhT\"}",
            getPreparedJsonDataFromFile("invoiceWebhookResponse.json")
        );

        // when
        Boolean result = this.getTestedClass(accessTokens).requestInvoiceWebhookToBeResent("Hpqc63wvE1ZjzeeH4kEycF", "cM78LHk17Q8fktDE6QLBBFfvH1QKBhRkHibTLcxhgzsu3VDRvSyu3CGi17DuwYxhT");
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_retrieve_an_invoice_event_token() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/invoices/GZRP3zgNHTDf8F5BmdChKz/events?token=" + MERCHANT_TOKEN,
            "GET",
            null,
            getPreparedJsonDataFromFile("getInvoiceEventToken.json")
        );

        // when
        InvoiceEventToken result = this.getTestedClass(accessTokens).getInvoiceEventToken("GZRP3zgNHTDf8F5BmdChKz");

        // then
        Assertions.assertEquals("4MuqDPt93i9Xbf8SnAPniwbGeNLW8A3ScgAmukFMgFUFRqTLuuhVdAFfePPysVqL2P", result.getToken());
        Assertions.assertEquals(Arrays.asList("payment", "confirmation"), result.getEvents());
        Assertions.assertEquals(Arrays.asList("subscribe", "unsubscribe"), result.getActions());
    }

    private Invoice getInvoiceExample() {
        Invoice invoice = new Invoice(10.0, "USD");
        invoice.setOrderId(EXAMPLE_UUID);
        invoice.setFullNotifications(true);
        invoice.setExtendedNotifications(true);
        invoice.setTransactionSpeed("medium");
        invoice.setNotificationUrl("https://notification.url/aaa");
        invoice.setItemDesc("Example");
        invoice.setNotificationEmail("m.warzybok@sumoheavy.com");
        invoice.setAutoRedirect(true);
        invoice.setForcedBuyerSelectedWallet("bitpay");
        Buyer buyerData = new Buyer();
        buyerData.setName("Marcin");
        buyerData.setAddress1("SomeStreet");
        buyerData.setAddress2("911");
        buyerData.setLocality("Washington");
        buyerData.setRegion("District of Columbia");
        buyerData.setPostalCode("20000");
        buyerData.setCountry("USA");
        buyerData.setEmail("buyer@buyeremaildomain.com");
        buyerData.setNotify(true);
        invoice.setBuyer(buyerData);

        return invoice;
    }

    private InvoiceClient getTestedClass(TokenContainer accessTokens) {
        return InvoiceClient.getInstance(this.getBitPayClient(), accessTokens, this.uuidGenerator);
    }
}
