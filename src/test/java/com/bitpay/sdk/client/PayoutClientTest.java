/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.ModelConfiguration;
import com.bitpay.sdk.model.payout.Payout;
import com.bitpay.sdk.util.TokenContainer;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PayoutClientTest extends AbstractClientTest {

    @Test
    public void it_should_submit_payout() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/payouts",
            "POST",
            getPreparedJsonDataFromFile("createPayoutRequest.json"),
            getPreparedJsonDataFromFile("createPayoutResponse.json")
        );

        // when
        Payout result = this.getTestedClass(accessTokens).submit(this.getExamplePayout());

        // then
        Assertions.assertEquals("SJcWZCFq344DL8QnXpdBNM", result.getAccountId());
        Assertions.assertEquals(10.0, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertNull(result.getDateExecuted());
        Assertions.assertEquals("2021-05-27T09:00Z", result.getEffectiveDate().toString());
        Assertions.assertEquals("john@doe.com", result.getEmail());
        Assertions.assertNull(result.getExchangeRates());
        Assertions.assertEquals("JMwv8wQCXANoU2ZZQ9a9GH", result.getId());
        Assertions.assertEquals("John Doe", result.getLabel());
        Assertions.assertEquals("GBP", result.getLedgerCurrency());
        Assertions.assertNull(result.getMessage());
        Assertions.assertEquals("merchant@email.com", result.getNotificationEmail());
        Assertions.assertEquals("https://yournotiticationURL.com/wed3sa0wx1rz5bg0bv97851eqx", result.getNotificationUrl());
        Assertions.assertEquals("LDxRZCGq174SF8AnQpdBPB", result.getRecipientId());
        Assertions.assertEquals("payout_20210527", result.getReference());
        Assertions.assertEquals("2021-05-27T10:47:37.834Z", result.getRequestDate().toString());
        Assertions.assertEquals("7qohDf2zZnQK5Qanj8oyC2", result.getShopperId());
        Assertions.assertEquals("new", result.getStatus());
        Assertions.assertEquals("6RZSTPtnzEaroAe2X4YijenRiqteRDNvzbT8NjtcHjUVd9FUFwa7dsX8RFgRDDC5SL", result.getToken());
        Assertions.assertEquals(0, result.getTransactions().size());
    }

    @Test
    public void it_should_get_payout() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/payouts/JMwv8wQCXANoU2ZZQ9a9GH?token=" + PAYOUT_TOKEN,
            "GET",
            null,
            getPreparedJsonDataFromFile("getPayoutResponse.json")
        );

        // when
        Payout result = this.getTestedClass(accessTokens).get("JMwv8wQCXANoU2ZZQ9a9GH");

        // then
        Assertions.assertEquals("SJcWZCFq344DL8QnXpdBNM", result.getAccountId());
        Assertions.assertEquals(10.0, result.getAmount());
        Assertions.assertEquals("USD", result.getCurrency());
        Assertions.assertEquals("2021-05-27T09:00Z", result.getDateExecuted().toString());
        Assertions.assertEquals("2021-05-27T09:00Z", result.getEffectiveDate().toString());
        Assertions.assertEquals("john@doe.com", result.getEmail());
        Assertions.assertEquals(27883.962246420004, result.getExchangeRates().get("BTC").get("GBP"));
        Assertions.assertEquals("JMwv8wQCXANoU2ZZQ9a9GH", result.getId());
        Assertions.assertEquals("John Doe", result.getLabel());
        Assertions.assertEquals("GBP", result.getLedgerCurrency());
        Assertions.assertNull(result.getMessage());
        Assertions.assertEquals("merchant@email.com", result.getNotificationEmail());
        Assertions.assertEquals("https://yournotiticationURL.com/wed3sa0wx1rz5bg0bv97851eqx", result.getNotificationUrl());
        Assertions.assertEquals("LDxRZCGq174SF8AnQpdBPB", result.getRecipientId());
        Assertions.assertEquals("payout_20210527", result.getReference());
        Assertions.assertEquals("2021-05-27T10:47:37.834Z", result.getRequestDate().toString());
        Assertions.assertEquals("7qohDf2zZnQK5Qanj8oyC2", result.getShopperId());
        Assertions.assertEquals("complete", result.getStatus());
        Assertions.assertEquals("6RZSTPtnzEaroAe2X4YijenRiqteRDNvzbT8NjtcHjUVd9FUFwa7dsX8RFgRDDC5SL", result.getToken());
        Assertions.assertEquals("db53d7e2bf3385a31257ce09396202d9c2823370a5ca186db315c45e24594057", result.getTransactions().get(0).getTxid());
        Assertions.assertEquals(6, result.getTransactions().get(0).getConfirmations());
    }

    @Test
    public void it_should_cancel_payout() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/payouts/KMXZeQigXG6T5abzCJmTcH?token=somePayoutToken",
            "DELETE",
            null,
            getPreparedJsonDataFromFile("cancelPayoutResponse.json")
        );

        // when
        Boolean result = this.getTestedClass(accessTokens).cancel("KMXZeQigXG6T5abzCJmTcH");

        // then
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_get_payouts() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/payouts?token=somePayoutToken&startDate=2021-05-27&endDate=2021-05-31",
            "GET",
            null,
            getPreparedJsonDataFromFile("getPayoutsResponse.json")
        );

        // when
        List<Payout> result = this.getTestedClass(accessTokens).getPayouts(
                "2021-05-27",
                "2021-05-31",
                null,
                null,
                null,
                null,
                null
            );

        // then
        Assertions.assertEquals(ModelConfiguration.DEFAULT_NON_SENT_VALUE, result.get(0).getAccountId());
        Assertions.assertEquals(10.0, result.get(0).getAmount());
        Assertions.assertEquals("USD", result.get(0).getCurrency());
        Assertions.assertNull(result.get(0).getDateExecuted());
        Assertions.assertEquals("2021-05-27T09:00Z", result.get(0).getEffectiveDate().toString());
        Assertions.assertEquals("john@doe.com", result.get(0).getEmail());
        Assertions.assertEquals("JMwv8wQCXANoU2ZZQ9a9GH", result.get(0).getId());
        Assertions.assertEquals("John Doe", result.get(0).getLabel());
        Assertions.assertEquals("GBP", result.get(0).getLedgerCurrency());
        Assertions.assertNull(result.get(0).getMessage());
        Assertions.assertEquals("merchant@email.com", result.get(0).getNotificationEmail());
        Assertions.assertEquals("https://yournotiticationURL.com/wed3sa0wx1rz5bg0bv97851eqx", result.get(0).getNotificationUrl());
        Assertions.assertEquals("LDxRZCGq174SF8AnQpdBPB", result.get(0).getRecipientId());
        Assertions.assertEquals("payout_20210527", result.get(0).getReference());
        Assertions.assertEquals("2021-05-27T10:47:37.834Z", result.get(0).getRequestDate().toString());
        Assertions.assertEquals("7qohDf2zZnQK5Qanj8oyC2", result.get(0).getShopperId());
        Assertions.assertEquals("complete", result.get(0).getStatus());
        Assertions.assertEquals("9pVLfvdjt59q1JiY2JEsf2uzeeEpSqDwwfRAzuFr9CcrxZX25rTnP6HdRhsMBGLArz", result.get(0).getToken());
        Assertions.assertEquals("db53d7e2bf3385a31257ce09396202d9c2823370a5ca186db315c45e24594057", result.get(0).getTransactions().get(0).getTxid());

        Assertions.assertEquals(ModelConfiguration.DEFAULT_NON_SENT_VALUE, result.get(1).getAccountId());
        Assertions.assertEquals(10.0, result.get(1).getAmount());
        Assertions.assertEquals("USD", result.get(1).getCurrency());
        Assertions.assertNull(result.get(1).getDateExecuted());
        Assertions.assertEquals("2021-05-28T09:00Z", result.get(1).getEffectiveDate().toString());
        Assertions.assertEquals("jane@doe.com", result.get(1).getEmail());
        Assertions.assertEquals("KMXZeQigXG6T5abzCJmTcH", result.get(1).getId());
        Assertions.assertEquals("Jane Doe", result.get(1).getLabel());
        Assertions.assertEquals("GBP", result.get(1).getLedgerCurrency());
        Assertions.assertNull(result.get(1).getMessage());
        Assertions.assertEquals("merchant@email.com", result.get(1).getNotificationEmail());
        Assertions.assertEquals("https://yournotiticationURL.com/wed3sa0wx1rz5bg0bv97851eqx", result.get(1).getNotificationUrl());
        Assertions.assertEquals("LDxRZCGq174SF8AnQpdBPB", result.get(1).getRecipientId());
        Assertions.assertEquals("payout_20210528", result.get(1).getReference());
        Assertions.assertEquals("2021-05-28T10:23:43.765Z", result.get(1).getRequestDate().toString());
        Assertions.assertEquals("7qohDf2zZnQK5Qanj8oyC2", result.get(1).getShopperId());
        Assertions.assertEquals("cancelled", result.get(1).getStatus());
        Assertions.assertEquals("9pVLfvdjt59q1JiY2JEsf2hr5FsjimfY4qRLFi85tMiXSCkJ9mQ2oSQqYKVangKaro", result.get(1).getToken());
        Assertions.assertEquals(0, result.get(1).getTransactions().size());
    }

    @Test
    public void it_should_request_payout_notification() throws BitPayException {
        // given
        TokenContainer accessTokens = this.getAccessTokens();
        this.addServerJsonResponse(
            "/payouts/JMwv8wQCXANoU2ZZQ9a9GH/notifications",
            "POST",
            getPreparedJsonDataFromFile("sendPayoutNotificationRequest.json"),
            getPreparedJsonDataFromFile("sendPayoutNotificationResponse.json")
        );

        // when
        Boolean result = this.getTestedClass(accessTokens).requestNotification("JMwv8wQCXANoU2ZZQ9a9GH");

        // then
        Assertions.assertTrue(result);
    }

    private Payout getExamplePayout() throws BitPayException {
        Payout payout = new Payout();
        payout.setToken(PAYOUT_TOKEN);
        payout.setAmount(10.00);
        payout.setCurrency("USD");
        payout.setLedgerCurrency("GBP");
        payout.setReference("payout_20210527");
        payout.setNotificationEmail("merchant@email.com");
        payout.setNotificationUrl("https://yournotiticationURL.com/wed3sa0wx1rz5bg0bv97851eqx");
        payout.setEmail("john@doe.com");
        payout.setLabel("John Doe");

        return payout;
    }

    private PayoutClient getTestedClass(TokenContainer accessTokens) {
        return PayoutClient.getInstance(this.getBitPayClient(), accessTokens);
    }
}
