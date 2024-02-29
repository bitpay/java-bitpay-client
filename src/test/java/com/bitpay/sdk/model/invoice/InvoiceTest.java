/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model.invoice;

import com.bitpay.sdk.exceptions.BitPayException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InvoiceTest {

    @Test
    public void it_should_manipulate_currency() throws BitPayException {
        final String expected = "PLN";
        Invoice testedClass = this.getTestedClass();

        testedClass.setCurrency(expected);
        Assertions.assertSame(expected, testedClass.getCurrency());
    }

    @Test
    public void it_should_not_allow_to_set_invalid_currency() {
        Assertions.assertThrows(BitPayException.class, () -> {
            final String invalidCurrency = "INVALID_CURRENCY";
            Invoice testedClass = this.getTestedClass();

            testedClass.setCurrency(invalidCurrency);
        });
    }

    @Test
    public void it_should_manipulate_guid() {
        String expected = "5016da97-dab1-4519-846e-aa7233b8dd6a";
        Invoice testedClass = this.getTestedClass();

        testedClass.setGuid(expected);
        Assertions.assertSame(expected, testedClass.getGuid());
    }

    @Test
    public void it_should_manipulate_token() {
        String expected = "8nPJSGgi7omxcbGGZ4KsSgqdi6juypBe9pVpSURDeAwx4VDQx1XfWPy5qqknDKT9KQ";
        Invoice testedClass = this.getTestedClass();

        testedClass.setToken(expected);
        Assertions.assertSame(expected, testedClass.getToken());
    }

    @Test
    public void it_should_manipulate_price() {
        Double expected = 10.00;
        Invoice testedClass = this.getTestedClass();

        testedClass.setPrice(expected);
        Assertions.assertSame(expected, testedClass.getPrice());
    }

    @Test
    public void it_should_manipulate_posData() {
        String expected = "98e572ea35hj356xft8y8cgh56h5090680f6";
        Invoice testedClass = this.getTestedClass();

        testedClass.setPosData(expected);
        Assertions.assertSame(expected, testedClass.getPosData());
    }

    @Test
    public void it_should_manipulate_notificationURL() {
        String expected = "https://hookbin.com/lJnJg9WW7MtG9GZlPVdj";
        Invoice testedClass = this.getTestedClass();

        testedClass.setNotificationUrl(expected);
        Assertions.assertSame(expected, testedClass.getNotificationUrl());
    }

    @Test
    public void it_should_manipulate_transactionSpeed() {
        Invoice testedClass = this.getTestedClass();
        String expected = "medium";

        testedClass.setTransactionSpeed(expected);
        Assertions.assertSame(expected, testedClass.getTransactionSpeed());
    }

    @Test
    public void it_should_manipulate_fullNotifications() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getFullNotifications());
        testedClass.setFullNotifications(expected);
        Assertions.assertSame(expected, testedClass.getFullNotifications());
    }

    @Test
    public void it_should_manipulate_notificationEmail() {
        String expected = "some@email.com";
        Invoice testedClass = this.getTestedClass();

        testedClass.setNotificationEmail(expected);
        Assertions.assertSame(expected, testedClass.getNotificationEmail());
    }

    @Test
    public void it_should_manipulate_redirectURL() {
        String expected = "https://hookbin.com/lJnJg9WW7MtG9GZlPVdj";
        Invoice testedClass = this.getTestedClass();

        testedClass.setRedirectUrl(expected);
        Assertions.assertSame(expected, testedClass.getRedirectUrl());
    }

    @Test
    public void it_should_manipulate_closeURL() {
        String expected = "https://close.com";
        Invoice testedClass = this.getTestedClass();

        testedClass.setCloseUrl(expected);
        Assertions.assertSame(expected, testedClass.getCloseUrl());
    }

    @Test
    public void it_should_manipulate_orderId() {
        String expected = "98e572ea-910e-415d-b6de-65f5090680f6";
        Invoice testedClass = this.getTestedClass();

        testedClass.setOrderId(expected);
        Assertions.assertSame(expected, testedClass.getOrderId());
    }

    @Test
    public void it_should_manipulate_itemDesc() {
        String expected = "Ab tempora sed ut.";
        Invoice testedClass = this.getTestedClass();

        testedClass.setItemDesc(expected);
        Assertions.assertSame(expected, testedClass.getItemDesc());
    }

    @Test
    public void it_should_manipulate_itemCode() {
        String expected = "bitcoindonation";
        Invoice testedClass = this.getTestedClass();

        testedClass.setItemCode(expected);
        Assertions.assertSame(expected, testedClass.getItemCode());
    }

    @Test
    public void it_should_manipulate_physical() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getPhysical());
        testedClass.setPhysical(expected);
        Assertions.assertSame(expected, testedClass.getPhysical());
    }

    @Test
    public void it_should_manipulate_paymentCurrencies() {
        List<String> expected = Arrays.asList("BTC", "ETH");
        Invoice testedClass = this.getTestedClass();

        testedClass.setPaymentCurrencies(expected);
        Assertions.assertSame(expected, testedClass.getPaymentCurrencies());
    }

    @Test
    public void it_should_manipulate_acceptanceWindow() {
        Integer expected = 10;
        Invoice testedClass = this.getTestedClass();

        testedClass.setAcceptanceWindow(expected);
        Assertions.assertSame(expected, testedClass.getAcceptanceWindow());
    }

    @Test
    public void it_should_manipulate_buyer() {
        Buyer expected = Mockito.mock(Buyer.class);
        Invoice testedClass = this.getTestedClass();

        testedClass.setBuyer(expected);
        Assertions.assertSame(expected, testedClass.getBuyer());
    }

    @Test
    public void it_should_manipulate_buyerSms() {
        String expected = "+13415556589";
        Invoice testedClass = this.getTestedClass();

        testedClass.setBuyerSms(expected);
        Assertions.assertSame(expected, testedClass.getBuyerSms());
    }

    @Test
    public void it_should_manipulate_merchantName() {
        String expected = "SomeString";
        Invoice testedClass = this.getTestedClass();

        testedClass.setMerchantName(expected);
        Assertions.assertSame(expected, testedClass.getMerchantName());
    }

    @Test
    public void it_should_manipulate_selectedTransactionCurrency() {
        String expected = "BTC";
        Invoice testedClass = this.getTestedClass();

        testedClass.setTransactionCurrency(expected);
        Assertions.assertSame(expected, testedClass.getTransactionCurrency());
    }

    @Test
    public void it_should_manipulate_forcedBuyerSelectedWallet() {
        String expected = "SomeString";
        Invoice testedClass = this.getTestedClass();

        testedClass.setForcedBuyerSelectedWallet(expected);
        Assertions.assertSame(expected, testedClass.getForcedBuyerSelectedWallet());
    }

    @Test
    public void it_should_manipulate_universalCodes() {
        InvoiceUniversalCodes expected = Mockito.mock(InvoiceUniversalCodes.class);
        Invoice testedClass = this.getTestedClass();

        testedClass.setUniversalCodes(expected);
        Assertions.assertSame(expected, testedClass.getUniversalCodes());
    }

    @Test
    public void it_should_manipulate_itemizedDetails() {
        List<InvoiceItemizedDetails> expected = Collections.singletonList(Mockito.mock(InvoiceItemizedDetails.class));
        Invoice testedClass = this.getTestedClass();

        testedClass.setItemizedDetails(expected);
        Assertions.assertSame(expected, testedClass.getItemizedDetails());
    }

    @Test
    public void it_should_manipulate_autoRedirect() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getAutoRedirect());
        testedClass.setAutoRedirect(expected);
        Assertions.assertSame(expected, testedClass.getAutoRedirect());
    }

    @Test
    public void it_should_manipulate_id() {
        String expected = "KSnNNfoMDsbRzd1U9ypmVH";
        Invoice testedClass = this.getTestedClass();

        testedClass.setId(expected);
        Assertions.assertSame(expected, testedClass.getId());
    }

    @Test
    public void it_should_manipulate_url() {
        String expected = "https://bitpay.com/invoice?id=KSnNNfoMDsbRzd1U9ypmVH";
        Invoice testedClass = this.getTestedClass();

        testedClass.setUrl(expected);
        Assertions.assertSame(expected, testedClass.getUrl());
    }

    @Test
    public void it_should_manipulate_status() {
        String expected = "new";
        Invoice testedClass = this.getTestedClass();

        testedClass.setStatus(expected);
        Assertions.assertSame(expected, testedClass.getStatus());
    }

    @Test
    public void it_should_manipulate_lowFeeDetected() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getLowFeeDetected());
        testedClass.setLowFeeDetected(expected);
        Assertions.assertSame(expected, testedClass.getLowFeeDetected());
    }

    @Test
    public void it_should_manipulate_invoiceTime() {
        long expected = 1620733980748L;
        Invoice testedClass = this.getTestedClass();

        testedClass.setInvoiceTime(expected);
        Assertions.assertEquals(expected, testedClass.getInvoiceTime());
    }

    @Test
    public void it_should_manipulate_expirationTime() {
        long expected = 1620734880748L;
        Invoice testedClass = this.getTestedClass();

        testedClass.setExpirationTime(expected);
        Assertions.assertEquals(expected, testedClass.getExpirationTime());
    }

    @Test
    public void it_should_manipulate_currentTime() {
        long expected = 1620733980807L;
        Invoice testedClass = this.getTestedClass();

        testedClass.setCurrentTime(expected);
        Assertions.assertEquals(expected, testedClass.getCurrentTime());
    }

    @Test
    public void it_should_manipulate_exceptionStatus() {
        String expected = "SomeString";
        Invoice testedClass = this.getTestedClass();

        testedClass.setExceptionStatus(expected);
        Assertions.assertSame(expected, testedClass.getExceptionStatus());
    }

    @Test
    public void it_should_manipulate_targetConfirmations() {
        Integer expected = 6;
        Invoice testedClass = this.getTestedClass();

        testedClass.setTargetConfirmations(expected);
        Assertions.assertSame(expected, testedClass.getTargetConfirmations());
    }

    @Test
    public void it_should_manipulate_transactions() {
        List<InvoiceTransaction> expected = Collections.singletonList(Mockito.mock(InvoiceTransaction.class));
        Invoice testedClass = this.getTestedClass();

        testedClass.setTransactions(expected);
        Assertions.assertSame(expected, testedClass.getTransactions());
    }

    @Test
    public void it_should_manipulate_refundAddresses() {
        List<Map<String, InvoiceRefundAddress>> expected =  new ArrayList<Map<String, InvoiceRefundAddress>>();
        Invoice testedClass = this.getTestedClass();

        testedClass.setRefundAddresses(expected);
        Assertions.assertSame(expected, testedClass.getRefundAddresses());
    }

    @Test
    public void it_should_manipulate_refundAddressRequestPending() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getRefundAddressRequestPending());
        testedClass.setRefundAddressRequestPending(expected);
        Assertions.assertSame(expected, testedClass.getRefundAddressRequestPending());
    }

    @Test
    public void it_should_manipulate_buyerProvidedEmail() {
        String expected = "some@email.com";
        Invoice testedClass = this.getTestedClass();

        testedClass.setBuyerProvidedEmail(expected);
        Assertions.assertSame(expected, testedClass.getBuyerProvidedEmail());
    }

    @Test
    public void it_should_manipulate_invoiceBuyerProvidedInfo() {
        InvoiceBuyerProvidedInfo expected = Mockito.mock(InvoiceBuyerProvidedInfo.class);
        Invoice testedClass = this.getTestedClass();

        testedClass.setInvoiceBuyerProvidedInfo(expected);
        Assertions.assertSame(expected, testedClass.getInvoiceBuyerProvidedInfo());
    }

    @Test
    public void it_should_manipulate_supportedTransactionCurrencies() {
        SupportedTransactionCurrencies expected = Mockito.mock(SupportedTransactionCurrencies.class);
        Invoice testedClass = this.getTestedClass();

        testedClass.setSupportedTransactionCurrencies(expected);
        Assertions.assertSame(expected, testedClass.getSupportedTransactionCurrencies());
    }

    @Test
    public void it_should_manipulate_minerFees() {
        MinerFees expected = Mockito.mock(MinerFees.class);
        Invoice testedClass = this.getTestedClass();

        testedClass.setMinerFees(expected);
        Assertions.assertSame(expected, testedClass.getMinerFees());
    }

    @Test
    public void it_should_manipulate_shopper() {
        Shopper expected = Mockito.mock(Shopper.class);
        Invoice testedClass = this.getTestedClass();

        testedClass.setShopper(expected);
        Assertions.assertSame(expected, testedClass.getShopper());
    }

    @Test
    public void it_should_manipulate_billId() {
        String expected = "SomeString";
        Invoice testedClass = this.getTestedClass();

        testedClass.setBillId(expected);
        Assertions.assertSame(expected, testedClass.getBillId());
    }

    @Test
    public void it_should_manipulate_refundInfo() {
        Invoice testedClass = this.getTestedClass();
        ArrayList<RefundInfo> expected = new ArrayList<>();
        expected.add(Mockito.mock(RefundInfo.class));

        testedClass.setRefundInfo(expected);
        Assertions.assertSame(expected, testedClass.getRefundInfo());
    }

    @Test
    public void it_should_manipulate_extendedNotifications() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getExtendedNotifications());
        testedClass.setExtendedNotifications(expected);
        Assertions.assertSame(expected, testedClass.getExtendedNotifications());
    }

    @Test
    public void it_should_manipulate_transactionCurrency() {
        String expected = "SomeString";
        Invoice testedClass = this.getTestedClass();

        testedClass.setTransactionCurrency(expected);
        Assertions.assertSame(expected, testedClass.getTransactionCurrency());
    }

    @Test
    public void it_should_manipulate_forcedBuyerSelectedTransactionCurrency() {
        String expected = "SomeString";
        Invoice testedClass = this.getTestedClass();

        testedClass.setForcedBuyerSelectedTransactionCurrency(expected);
        Assertions.assertSame(expected, testedClass.getForcedBuyerSelectedTransactionCurrency());
    }

    @Test
    public void it_should_manipulate_amountPaid() {
        BigDecimal expected = BigDecimal.valueOf(20);
        Invoice testedClass = this.getTestedClass();

        testedClass.setAmountPaid(expected);
        Assertions.assertSame(expected, testedClass.getAmountPaid());
    }

    @Test
    public void it_should_manipulate_displayAmountPaid() {
        String expected = "20";
        Invoice testedClass = this.getTestedClass();

        testedClass.setDisplayAmountPaid(expected);
        Assertions.assertSame(expected, testedClass.getDisplayAmountPaid());
    }

    @Test
    public void it_should_manipulate_exchangeRates() {
        Hashtable<String, Hashtable<String, BigDecimal>> expected = new Hashtable<>();
        Invoice testedClass = this.getTestedClass();

        testedClass.setExchangeRates(expected);
        Assertions.assertSame(expected, testedClass.getExchangeRates());
    }

    @Test
    public void it_should_manipulate_isCancelled() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getIsCancelled());
        testedClass.setIsCancelled(expected);
        Assertions.assertSame(expected, testedClass.getIsCancelled());
    }

    @Test
    public void it_should_manipulate_bitpayIdRequired() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getBitpayIdRequired());
        testedClass.setBitpayIdRequired(expected);
        Assertions.assertSame(expected, testedClass.getBitpayIdRequired());
    }

    @Test
    public void it_should_manipulate_paymentSubtotals() {
        Hashtable<String, BigInteger> expected = new Hashtable<>();
        Invoice testedClass = this.getTestedClass();

        testedClass.setPaymentSubTotals(expected);
        Assertions.assertSame(expected, testedClass.getPaymentSubTotals());
    }

    @Test
    public void it_should_manipulate_paymentTotals() {
        Hashtable<String, BigInteger> expected = new Hashtable<>();
        Invoice testedClass = this.getTestedClass();

        testedClass.setPaymentTotals(expected);
        Assertions.assertSame(expected, testedClass.getPaymentTotals());
    }

    @Test
    public void it_should_manipulate_paymentDisplayTotals() {
        Hashtable<String, String> expected = new Hashtable<>();
        Invoice testedClass = this.getTestedClass();

        testedClass.setPaymentDisplayTotals(expected);
        Assertions.assertSame(expected, testedClass.getPaymentDisplayTotals());
    }

    @Test
    public void it_should_manipulate_paymentDisplaySubTotals() {
        Hashtable<String, String> expected = new Hashtable<>();
        Invoice testedClass = this.getTestedClass();

        testedClass.setPaymentDisplaySubTotals(expected);
        Assertions.assertSame(expected, testedClass.getPaymentDisplaySubTotals());
    }

    @Test
    public void it_should_manipulate_nonPayProPaymentReceived() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getNonPayProPaymentReceived());
        testedClass.setNonPayProPaymentReceived(expected);
        Assertions.assertSame(expected, testedClass.getNonPayProPaymentReceived());
    }

    @Test
    public void it_should_manipulate_jsonPayProRequired() {
        boolean expected = true;
        Invoice testedClass = this.getTestedClass();

        Assertions.assertSame(null, testedClass.getJsonPayProRequired());
        testedClass.setJsonPayProRequired(expected);
        Assertions.assertSame(expected, testedClass.getJsonPayProRequired());
    }

    @Test
    public void it_should_manipulate_underpaidAmount() {
        BigDecimal expected = BigDecimal.TEN;
        Invoice testedClass = this.getTestedClass();

        testedClass.setUnderPaidAmount(expected);
        Assertions.assertSame(expected, testedClass.getUnderPaidAmount());
    }

    @Test
    public void it_should_manipulate_overpaidAmount() {
        BigDecimal expected = BigDecimal.TEN;
        Invoice testedClass = this.getTestedClass();

        testedClass.setOverPaidAmount(expected);
        Assertions.assertSame(expected, testedClass.getOverPaidAmount());
    }

    @Test
    public void it_should_manipulate_paymentCodes() {
        Hashtable<String, Hashtable<String, String>> expected = new Hashtable<>();
        Invoice testedClass = this.getTestedClass();

        testedClass.setPaymentCodes(expected);
        Assertions.assertSame(expected, testedClass.getPaymentCodes());
    }

    private Invoice getTestedClass() {
        return new Invoice();
    }
}
