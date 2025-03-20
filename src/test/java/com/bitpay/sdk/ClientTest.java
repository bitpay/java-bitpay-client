/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

import com.bitpay.sdk.client.BitPayClient;
import com.bitpay.sdk.client.HttpResponse;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.exceptions.BitPayValidationException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.bill.Bill;
import com.bitpay.sdk.model.bill.Item;
import com.bitpay.sdk.model.invoice.Buyer;
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.model.invoice.InvoiceEventToken;
import com.bitpay.sdk.model.invoice.Refund;
import com.bitpay.sdk.model.ledger.Ledger;
import com.bitpay.sdk.model.ledger.LedgerEntry;
import com.bitpay.sdk.model.payout.Payout;
import com.bitpay.sdk.model.payout.PayoutGroup;
import com.bitpay.sdk.model.payout.PayoutGroupFailed;
import com.bitpay.sdk.model.payout.PayoutRecipient;
import com.bitpay.sdk.model.payout.PayoutRecipients;
import com.bitpay.sdk.model.rate.Rate;
import com.bitpay.sdk.model.rate.Rates;
import com.bitpay.sdk.model.settlement.Settlement;
import com.bitpay.sdk.model.wallet.Wallet;
import com.bitpay.sdk.util.GuidGenerator;
import com.bitpay.sdk.util.TokenContainer;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    protected static final String EXAMPLE_UUID = "37bd36bd-6fcb-409c-a907-47f9244302aa";
    private static final String IDENTITY = "Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW";
    protected static final String BILL_ID = "X6KJbe9RxAGWNReCwd1xRw";
    protected static final String MERCHANT_TOKEN = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";
    protected static final String PAYOUT_ACCESS_TOKEN = "3LKKrrNB2BcVAu2Y24QQ78GrKUk2ANLK4eLo85Q1a2HU";
    protected static final String RECIPIENT_ID = "JA4cEtmBxCp5cybtnh1rds";
    protected static final String RECIPIENT_TOKEN =
        "2LVBntm7z92rnuVjVX5ZVaDoUEaoY4LxhZMMzPAMGyXcejgPXVmZ4Ae3oGaCGBFKQf";
    protected static final String PAYOUT_TOKEN = "3tDEActqHSjbc3Hn5MoLH7XTn4hMdGSp6YbmvNDXTr5Y";
    protected static final String PAYOUT_ID = "JMwv8wQCXANoU2ZZQ9a9GH";

    @Mock
    private BitPayClient bitPayClient;
    @Mock
    private TokenContainer accessTokens;
    @Mock
    private GuidGenerator guidGenerator;

    @BeforeEach
    public void clearResourceClients() {
        ResourceClientsCleaner.execute();
    }

    @Test
    public void it_should_provide_pos_client() throws BitPayGenericException {
        // given
        String posToken = "posToken";

        // when
        Client bitpay = Client.createPosClient(new PosToken(posToken));

        // then
        Assertions.assertEquals(posToken, bitpay.getAccessToken(Facade.POS));
    }

    @Test
    public void it_should_provide_pos_client_with_platform_info_header() throws BitPayGenericException {
        // given
        String posToken = "posToken";

        // when
        Client bitpay = Client.createPosClient(new PosToken(posToken), "MyPlatform_v1.0.0");

        // then
        Assertions.assertEquals(posToken, bitpay.getAccessToken(Facade.POS));
    }

    @Test
    public void it_should_provide_client_by_key() throws BitPayGenericException {
        // given
        String privateKey =
            "3082013102010104208ae30afbc7e93cb10cb983f70863e546b53f0b2c6158b1a71b576fd09790cff3a081e33081e0020101302c06072a8648ce3d0101022100fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f3044042000000000000000000000000000000000000000000000000000000000000000000420000000000000000000000000000000000000000000000000000000000000000704410479be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8022100fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141020101a124032200035d6a7e38d7c08b8a626e2390d0360a72a58bd1c5e1348e0eb810d4bbab3d3adf";
        String merchantToken = "merchantToken";
        TokenContainer tokens = new TokenContainer();
        tokens.addMerchant(merchantToken);

        // when
        Client bitpay = Client.createClientByPrivateKey(new PrivateKey(privateKey), tokens, Environment.TEST);

        // then
        Assertions.assertEquals(merchantToken, bitpay.getAccessToken(Facade.MERCHANT));
    }

    @Test
    public void it_should_provide_client_by_key_with_platform_info_header() throws BitPayGenericException {
        // given
        String privateKey =
            "3082013102010104208ae30afbc7e93cb10cb983f70863e546b53f0b2c6158b1a71b576fd09790cff3a081e33081e0020101302c06072a8648ce3d0101022100fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f3044042000000000000000000000000000000000000000000000000000000000000000000420000000000000000000000000000000000000000000000000000000000000000704410479be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8022100fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141020101a124032200035d6a7e38d7c08b8a626e2390d0360a72a58bd1c5e1348e0eb810d4bbab3d3adf";
        String merchantToken = "merchantToken";
        TokenContainer tokens = new TokenContainer();
        tokens.addMerchant(merchantToken);

        // when
        Client bitpay = Client.createClientByPrivateKey(new PrivateKey(privateKey), tokens, Environment.TEST, "MyPlatform_v1.0.0");

        // then
        Assertions.assertEquals(merchantToken, bitpay.getAccessToken(Facade.MERCHANT));
    }

    @Test
    public void it_should_provide_client_by_config() throws BitPayGenericException {
        // given
        String path = System.getProperty("user.dir") + "/src/test/java/com/bitpay/sdk/BitPay.config.json";

        // when
        Client bitpay = Client.createClientByConfigFilePath(new ConfigFilePath(path));

        // then
        Assertions.assertEquals("merchantToken", bitpay.getAccessToken(Facade.MERCHANT));
        Assertions.assertEquals("payoutToken", bitpay.getAccessToken(Facade.PAYOUT));
    }

    @Test
    public void it_should_provide_client_by_config_with_platform_info_header() throws BitPayGenericException {
        // given
        String path = System.getProperty("user.dir") + "/src/test/java/com/bitpay/sdk/BitPay.config.json";

        // when
        Client bitpay = Client.createClientByConfigFilePath(new ConfigFilePath(path), "MyPlatform_v1.0.0");

        // then
        Assertions.assertEquals("merchantToken", bitpay.getAccessToken(Facade.MERCHANT));
        Assertions.assertEquals("payoutToken", bitpay.getAccessToken(Facade.PAYOUT));
    }

    @Test
    public void it_should_throws_BitPayApiException_for_invalid_privateKey() {
        BitPayGenericException exception = Assertions.assertThrows(BitPayGenericException.class, () -> {
            new Client(
                Environment.TEST,
                new PrivateKey("invalid"),
                Mockito.mock(TokenContainer.class),
                new HttpHost("http://localhost"),
                Mockito.mock(CredentialsProvider.class)
            );
        });

        Assertions.assertEquals("Private Key file not found", exception.getMessage());
    }

    @Test
    public void it_should_authorize_client_by_pairing_code() throws BitPayApiException, BitPayGenericException {
        // given
        String pairingCode = "123123123";
        Mockito.when(this.guidGenerator.execute()).thenReturn(EXAMPLE_UUID);
        final String responseString =
            "[{\"policies\":[{\"policy\":\"id\",\"method\":\"active\",\"params\":[\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\"]}],\"token\":\"t0k3n\",\"facade\":\"merchant\",\"dateCreated\":1668425446554,\"pairingExpiration\":1668511846554,\"pairingCode\":\"123123123\"}]";
        Mockito.when(this.bitPayClient.post("tokens",
            "{\"guid\":\"37bd36bd-6fcb-409c-a907-47f9244302aa\",\"id\":\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\",\"pairingCode\":\"123123123\"}"))
            .thenReturn(this.getHttpResponseWithSpecificBody(responseString));

        Client testedClass = this.getTestedClass();

        // when
        testedClass.authorizeClient(pairingCode);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).put("merchant", "t0k3n");
    }

    @Test
    public void it_should_authorize_client_by_facade() throws BitPayApiException, BitPayGenericException {
        // given
        Mockito.when(this.guidGenerator.execute()).thenReturn(EXAMPLE_UUID);

        final String responseString =
            "[{\"policies\":[{\"policy\":\"id\",\"method\":\"inactive\",\"params\":[\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\"]}],\"token\":\"G7XM9fcM1gtCN7DUr8ZWtPGVFLTKiYWanHR4kvqsnjP3\",\"facade\":\"merchant\",\"label\":\"merchantwebsite.com\",\"dateCreated\":1621340364865,\"pairingExpiration\":1621426764865,\"pairingCode\":\"C4Lg7oW\"}]";
        Mockito.when(this.bitPayClient.post("tokens",
            "{\"count\":1,\"facade\":\"merchant\",\"guid\":\"37bd36bd-6fcb-409c-a907-47f9244302aa\",\"id\":\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\"}"))
            .thenReturn(this.getHttpResponseWithSpecificBody(responseString));

        Client testedClass = this.getTestedClass();

        // when
        testedClass.authorizeClient(Facade.MERCHANT);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1))
            .put("merchant", "G7XM9fcM1gtCN7DUr8ZWtPGVFLTKiYWanHR4kvqsnjP3");
    }

    @Test
    public void it_should_test_requestClientAuthorization() throws BitPayApiException, BitPayGenericException {
        // given
        String pairingCode = "123123123";
        Mockito.when(this.guidGenerator.execute()).thenReturn(EXAMPLE_UUID);
        final String responseString =
            "[{\"policies\":[{\"policy\":\"id\",\"method\":\"active\",\"params\":[\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\"]}],\"token\":\"t0k3n\",\"facade\":\"merchant\",\"dateCreated\":1668425446554,\"pairingExpiration\":1668511846554,\"pairingCode\":\"\"}]";
        Mockito.when(this.bitPayClient.post("tokens",
            "{\"guid\":\"37bd36bd-6fcb-409c-a907-47f9244302aa\",\"id\":\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\",\"pairingCode\":\"123123123\"}"))
            .thenReturn(this.getHttpResponseWithSpecificBody(responseString));

        Client testedClass = this.getTestedClass();

        // when
        testedClass.authorizeClient(pairingCode);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).put("merchant", "t0k3n");
    }

    @Test
    public void it_should_test_getAccessToken() throws BitPayGenericException {
        // given
        Client testedClass = this.getTestedClass();
        String tokenKey = "tokenKey";

        // when
        testedClass.getAccessToken(tokenKey);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(tokenKey);
    }

    @Test
    public void it_should_test_getCurrencyInfo() throws BitPayGenericException, BitPayApiException {
        // given
        Mockito.when(this.bitPayClient.get("currencies")).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("currencies.json")));

        Client testedClass = this.getTestedClass();

        // when
        Map<String, Object> result = testedClass.getCurrencyInfo("USD");

        // then
        Assertions.assertEquals("USD", result.get("code"));
        Assertions.assertEquals("$", result.get("symbol"));
        Assertions.assertEquals("US Dollar", result.get("name"));
    }

    @Test
    public void it_should_test_create_invoice_by_merchant()
        throws BitPayGenericException, BitPayApiException {
        // given
        Invoice invoice = getInvoiceExample();

        Mockito.when(this.bitPayClient.post("invoices", getPreparedJsonDataFromFile("createInvoiceRequest.json"), true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("createInvoiceResponse.json")));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(true);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT))
            .thenReturn("someToken");
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.createInvoice(invoice);

        // then
        Mockito.verify(this.guidGenerator, Mockito.times(1)).execute();
        Mockito.verify(this.bitPayClient, Mockito.times(1))
            .post("invoices", getPreparedJsonDataFromFile("createInvoiceRequest.json"), true);
        Mockito.verify(this.accessTokens, Mockito.times(1))
            .put(
                "UZjwcYkWAKfTMn9J1yyfs4",
                "4qS4CzeRlGRu9VvUfVvuESfQXWTAQuFLjhj6osrGexKWZoadBPe1eiScsvTX99dkYi"
            );
        Assertions.assertEquals("UZjwcYkWAKfTMn9J1yyfs4", result.getId());
        Assertions.assertEquals("2024-01-08T23:50:56.556Z", result.getRefundAddresses().get(0).get("n2MDYgEhxCAnuoVd1JpPmvxZShE6rQA6zv").getDate().toString());
    }

    @Test
    public void it_should_test_createInvoice_by_pos() throws BitPayGenericException, BitPayApiException {
        // given
        Invoice invoice = getInvoiceExample();

        Mockito
            .when(this.bitPayClient.post("invoices", getPreparedJsonDataFromFile("createInvoiceRequest.json"), false))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("createInvoiceResponse.json")));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(false);
        Mockito.when(this.accessTokens.getAccessToken(Facade.POS)).thenReturn("someToken");
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.createInvoice(invoice);

        // then
        Mockito.verify(this.guidGenerator, Mockito.times(1)).execute();
        Mockito.verify(this.bitPayClient, Mockito.times(1))
            .post("invoices", getPreparedJsonDataFromFile("createInvoiceRequest.json"), false);
        Mockito.verify(this.accessTokens, Mockito.times(1))
            .put(
                "UZjwcYkWAKfTMn9J1yyfs4",
                "4qS4CzeRlGRu9VvUfVvuESfQXWTAQuFLjhj6osrGexKWZoadBPe1eiScsvTX99dkYi"
            );
        Assertions.assertEquals("UZjwcYkWAKfTMn9J1yyfs4", result.getId());
    }

    @Test
    public void it_should_test_getInvoice_by_merchant() throws BitPayApiException, BitPayGenericException {
        // given
        String id = "UZjwcYkWAKfTMn9J1yyfs4";
        final String facadeToken = "someToken";
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", facadeToken));

        Mockito.when(this.bitPayClient
            .get(ArgumentMatchers.eq("invoices/" + id), ArgumentMatchers.eq(params), ArgumentMatchers.eq(true)))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getInvoice.json")));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(true);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT))
            .thenReturn(facadeToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.getInvoice(id);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1))
            .get(ArgumentMatchers.eq("invoices/" + id), ArgumentMatchers.eq(params), ArgumentMatchers.eq(true));
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
        Assertions.assertEquals(new BigInteger("12502605000000000000"), result.getPaymentSubTotals().get("MATIC"));
        Assertions.assertEquals(new BigDecimal("0.000058414627022606464"), result.getExchangeRates().get("GUSD").get("BTC"));
    }

    @Test
    public void it_should_test_getInvoice_by_pos() throws BitPayApiException, BitPayGenericException {
        // given
        String id = "UZjwcYkWAKfTMn9J1yyfs4";
        final String facadeToken = "someToken";
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", facadeToken));

        Mockito.when(this.bitPayClient
            .get(ArgumentMatchers.eq("invoices/" + id), ArgumentMatchers.eq(params), ArgumentMatchers.eq(false)))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getInvoice.json")));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(false);
        Mockito.when(this.accessTokens.getAccessToken(Facade.POS)).thenReturn(facadeToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.getInvoice(id);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1))
            .get(ArgumentMatchers.eq("invoices/" + id), ArgumentMatchers.eq(params), ArgumentMatchers.eq(false));
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_getInvoiceByGuid() throws BitPayApiException, BitPayGenericException {
        // given
        String guid = "chc9kj52-04g0-4b6f-941d-3a844e352758";
        String merchantToken = "merchantToken";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));
        Mockito.when(this.bitPayClient
            .get(ArgumentMatchers.eq("invoices/guid/" + guid), ArgumentMatchers.eq(expectedParams),
                ArgumentMatchers.eq(true)))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getInvoice.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.getInvoiceByGuid(guid, Facade.MERCHANT, true);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_getInvoices() throws BitPayApiException, BitPayGenericException {
        // given
        String merchantToken = "merchantToken";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));
        expectedParams.add(new BasicNameValuePair("dateStart", "2022-5-10"));
        expectedParams.add(new BasicNameValuePair("dateEnd", "2022-5-11"));
        expectedParams.add(new BasicNameValuePair("status", "complete"));
        expectedParams.add(new BasicNameValuePair("limit", "1"));
        Mockito.when(this.bitPayClient.get(ArgumentMatchers.eq("invoices"), ArgumentMatchers.eq(expectedParams)))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getInvoices.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        List<Invoice> result = testedClass.getInvoices(
            "2022-5-10",
            "2022-5-11",
            "complete",
            null,
            1,
            null
        );

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void it_should_get_invoice_event_token() throws BitPayApiException, BitPayGenericException {
        // given
        String merchantToken = "merchantToken";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));
        Mockito.when(this.bitPayClient.get(ArgumentMatchers.eq("invoices/GZRP3zgNHTDf8F5BmdChKz/events"), ArgumentMatchers.eq(expectedParams)))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getInvoiceEventToken.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        InvoiceEventToken result = testedClass.getInvoiceEventToken("GZRP3zgNHTDf8F5BmdChKz");

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Assertions.assertEquals("4MuqDPt93i9Xbf8SnAPniwbGeNLW8A3ScgAmukFMgFUFRqTLuuhVdAFfePPysVqL2P", result.getToken());
        Assertions.assertEquals(Arrays.asList("payment", "confirmation"), result.getEvents());
        Assertions.assertEquals(Arrays.asList("subscribe", "unsubscribe"), result.getActions());
    }

    @Test
    public void it_should_test_throws_validation_exception_for_missing_arguments_in_updateInvoice() throws BitPayValidationException {
        Assertions.assertThrows(BitPayValidationException.class, () -> {
            // given
            final String merchantToken = "merchantToken";
            final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
            Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
            Client testedClass = this.getTestedClass();

            // when
            testedClass.updateInvoice(
                invoiceId,
                null,
                null,
                null,
                null
            );

            // missing buyerSms or smsCode
        });
    }

    @Test
    public void it_should_test_updateInvoice() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";

        Mockito.when(this.bitPayClient.update(ArgumentMatchers.eq("invoices/" + invoiceId),
            ArgumentMatchers.eq("{\"buyerSms\":\"+12223334444\",\"token\":\"merchantToken\"}")))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getInvoice.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);

        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.updateInvoice(
            invoiceId,
            "+12223334444",
            null,
            null,
            null
        );

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_payInvoice() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        final String status = "complete";

        Mockito.when(this.bitPayClient.update(ArgumentMatchers.eq("invoices/pay/" + invoiceId),
            ArgumentMatchers.eq("{\"token\":\"merchantToken\",\"status\":\"complete\"}")))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getInvoice.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.payInvoice(
            invoiceId,
            status
        );

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_force_cancel_invoice() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));
        expectedParams.add(new BasicNameValuePair("forceCancel", "true"));

        Mockito.when(this.bitPayClient.delete(
            ArgumentMatchers.eq("invoices/" + invoiceId), ArgumentMatchers.eq(expectedParams))
        ).thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getCancelledInvoice.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.cancelInvoice(invoiceId, true);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("invoices/" + invoiceId, expectedParams);
        Assertions.assertEquals("payment#1234", result.getGuid());
    }

    @Test
    public void it_should_cancel_invoice() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));

        Mockito.when(this.bitPayClient.delete(
            ArgumentMatchers.eq("invoices/" + invoiceId), ArgumentMatchers.eq(expectedParams))
        ).thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getCancelledInvoice.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.cancelInvoice(invoiceId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("invoices/" + invoiceId, expectedParams);
        Assertions.assertEquals("payment#1234", result.getGuid());
    }

    @Test
    public void it_should_cancel_invoice_by_guid() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String guidId = "payment#1234";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));

        Mockito.when(this.bitPayClient.delete(
            ArgumentMatchers.eq("invoices/guid/" + guidId), ArgumentMatchers.eq(expectedParams))
        ).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getCancelledInvoice.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.cancelInvoiceByGuid(guidId, false);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("invoices/guid/" + guidId, expectedParams);
        Assertions.assertEquals(guidId, result.getGuid());
    }

    @Test
    public void it_should_throws_exception_for_cancel_invoice_for_invalid_state_of_cancel() throws BitPayApiException {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final String merchantToken = "merchantToken";
                final String guidId = "chc9kj52-04g0-4b6f-941d-3a844e352757";
                List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
                expectedParams.add(new BasicNameValuePair("token", merchantToken));

                Mockito.when(this.bitPayClient.delete(
                    ArgumentMatchers.eq("invoices/guid/" + guidId), ArgumentMatchers.eq(expectedParams))
                ).thenThrow(new BitPayApiException("Error: Invalid invoice state for cancel", "00000"));
                Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
                Client testedClass = this.getTestedClass();

                // when
                testedClass.cancelInvoiceByGuid(guidId, false);
            }
        );

        Assertions.assertEquals(
            "Error: Invalid invoice state for cancel",
            exception.getMessage()
        );

        Assertions.assertEquals(
            "00000",
            exception.getCode()
        );
    }

    @Test
    public void it_should_request_invoice_webhook_to_be_resent() throws BitPayApiException, BitPayGenericException {
        // given
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        final String invoiceToken = "cM78LHk17Q8fktDE6QLBBFfvH1QKBhRkHibTLcxhgzsu3VDRvSyu3CGi17DuwYxhT";
        final String requestJson = String.format("{\"token\":\"%s\"}", invoiceToken);

        Mockito.when(this.bitPayClient.post(
            ArgumentMatchers.eq("invoices/" + invoiceId + "/notifications"), ArgumentMatchers.eq(requestJson))
        ).thenReturn(this.getHttpResponseWithSpecificBody("{\"data\": \"Success\"}"));
        Client testedClass = this.getTestedClass();

        // when
        Boolean result = testedClass.requestInvoiceWebhookToBeResent(invoiceId, invoiceToken);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("invoices/" + invoiceId + "/notifications", requestJson);
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_create_refund() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        final String createRefundJsonRequest = getPreparedJsonDataFromFile("createRefundRequest.json");

        Mockito.when(this.bitPayClient.post("refunds/", createRefundJsonRequest, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("createRefundResponse.json")));
        Mockito.when(this.guidGenerator.execute()).thenReturn(EXAMPLE_UUID);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.createRefund(
            invoiceId,
            10.00,
            true,
            false,
            false,
            "someReference"
        );

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "refunds/",
            createRefundJsonRequest,
            true
        );
        Assertions.assertEquals(invoiceId, result.getInvoice());
        Assertions.assertEquals(EXAMPLE_UUID, result.getGuid());
    }

    @Test
    public void it_should_create_refund_with_guid() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        final String createRefundJsonRequest = getPreparedJsonDataFromFile("createRefundRequest.json");

        Mockito.when(this.bitPayClient.post("refunds/", createRefundJsonRequest, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("createRefundResponse.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.createRefund(
            invoiceId,
            10.00,
            true,
            false,
            false,
            "someReference",
            EXAMPLE_UUID
        );

        // then
        Mockito.verify(this.guidGenerator, Mockito.times(0)).execute();
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "refunds/",
            createRefundJsonRequest,
            true
        );
        Assertions.assertEquals(invoiceId, result.getInvoice());
        Assertions.assertEquals(EXAMPLE_UUID, result.getGuid());
    }

    @Test
    public void it_should_create_refund_using_refund_object() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        final String createRefundJsonRequest = getPreparedJsonDataFromFile("createRefundRequest.json");

        Mockito.when(this.bitPayClient.post("refunds/", createRefundJsonRequest, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("createRefundResponse.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();
        Refund refundRequestObject = new Refund();
        refundRequestObject.setPreview(true);
        refundRequestObject.setReference("someReference");
        refundRequestObject.setAmount(10.00);
        refundRequestObject.setImmediate(false);
        refundRequestObject.setInvoice("UZjwcYkWAKfTMn9J1yyfs4");
        refundRequestObject.setBuyerPaysRefundFee(false);
        refundRequestObject.setGuid("37bd36bd-6fcb-409c-a907-47f9244302aa");

        // when
        Refund result = testedClass.createRefund(refundRequestObject);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("refunds/", createRefundJsonRequest, true);
        Mockito.verify(this.guidGenerator, Mockito.times(0)).execute();
        Assertions.assertEquals(invoiceId, result.getInvoice());
        Assertions.assertEquals(EXAMPLE_UUID, result.getGuid());
    }

    @Test
    public void it_should_throws_bitpay_validation_exception_for_missing_invoice_id_and_amount_for_createRefund() {
        BitPayValidationException exception = Assertions.assertThrows(
            BitPayValidationException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();

                // when
                testedClass.createRefund(null, null, true, true, true, "no");
            }
        );

        Assertions.assertEquals(
            "Invoice ID, amount and currency are required to issue a refund.",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpay_api_exception_for_invalid_sendRefundNotification() {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                Mockito.when(this.bitPayClient.post(
                    ArgumentMatchers.eq("refunds/1/notifications"),
                    ArgumentMatchers.anyString(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayApiException("error message", "500"));

                // when
                testedClass.sendRefundNotification("1", "cM78LHk17Q8fktDE6QLBBFfvH1QKBhRkHibTLcxhgzsu3VDRvSyu3CGi17DuwYxhT");
            }
        );

        Assertions.assertEquals(
            "error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpay_api_exception_for_api_issue_for_create_refund() {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                Mockito.when(this.bitPayClient.post(
                    ArgumentMatchers.eq("refunds/"),
                    ArgumentMatchers.anyString(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayApiException("error message", "500"));

                // when
                testedClass.createRefund("123", 2.20, true, true, true, "no");
            }
        );

        Assertions.assertEquals(
            "error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpay_api_exception_for_api_issue_for_get_refund() {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                String id = "12";
                Mockito.when(this.bitPayClient.get(
                    ArgumentMatchers.eq("refunds/" + id),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayApiException("error message", "500"));

                // when
                testedClass.getRefund(id);
            }
        );

        Assertions.assertEquals(
            "error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpay_api_exception_for_api_issue_for_get_refunds() {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                String id = "12";
                Mockito.when(this.bitPayClient.get(
                    ArgumentMatchers.eq("refunds/"),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayApiException("error message", "500"));

                // when
                testedClass.getRefunds(id);
            }
        );

        Assertions.assertEquals(
            "error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpay_api_exception_for_api_issue_for_update_refund() {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                String id = "12";
                Mockito.when(this.bitPayClient.update(
                    ArgumentMatchers.eq("refunds/12"),
                    ArgumentMatchers.eq("{\"token\":null,\"status\":\"complete\"}")
                )).thenThrow(new BitPayApiException("error message", "500"));

                // when
                testedClass.updateRefund(id, "complete");
            }
        );

        Assertions.assertEquals(
            "error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_get_refund_by_id() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String refundId = "WoE46gSLkJQS48RJEiNw3L";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", "merchantToken"));

        Mockito.when(this.bitPayClient.get("refunds/" + refundId, params, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getRefund.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);

        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.getRefund(refundId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get(
            "refunds/" + refundId,
            params,
            true
        );
        Assertions.assertEquals(refundId, result.getId());
    }

    @Test
    public void it_should_get_refund_by_guid() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", "merchantToken"));

        Mockito.when(this.bitPayClient.get("refunds/guid/" + EXAMPLE_UUID, params, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getRefund.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);

        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.getRefundByGuid(EXAMPLE_UUID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get(
            "refunds/guid/" + EXAMPLE_UUID,
            params,
            true
        );
        Assertions.assertEquals(EXAMPLE_UUID, result.getGuid());
    }

    @Test
    public void it_should_test_get_refunds() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "Hpqc63wvE1ZjzeeH4kEycF";
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("getRefunds.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));
        params.add(new BasicNameValuePair("invoiceId", invoiceId));

        Mockito.when(this.bitPayClient.get("refunds/", params, true))
            .thenReturn(this.getHttpResponseWithSpecificBody(getRefundsJsonConvertedResponse));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        List<Refund> result = testedClass.getRefunds(invoiceId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get(
            "refunds/",
            params,
            true
        );
        Assertions.assertEquals(invoiceId, result.get(0).getInvoice());
    }

    @Test
    public void it_should_update_refund_by_id() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String status = "complete";
        final String refundId = "WoE46gSLkJQS48RJEiNw3L";
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("getRefund.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));
        params.add(new BasicNameValuePair("status", "complete"));
        final String requestedJson = "{\"token\":\"merchantToken\",\"status\":\"complete\"}";

        Mockito.when(this.bitPayClient.update(
            "refunds/" + refundId,
            requestedJson
        )).thenReturn(this.getHttpResponseWithSpecificBody(getRefundsJsonConvertedResponse));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.updateRefund(refundId, status);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).update("refunds/" + refundId, requestedJson);
        Assertions.assertEquals(refundId, result.getId());
    }

    @Test
    public void it_should_update_refund_by_guid() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String status = "complete";
        final String guid = EXAMPLE_UUID;
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("getRefund.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));
        params.add(new BasicNameValuePair("status", "complete"));
        final String requestedJson = "{\"token\":\"merchantToken\",\"status\":\"complete\"}";

        Mockito.when(this.bitPayClient.update(
            "refunds/guid/" + guid,
            requestedJson
        )).thenReturn(this.getHttpResponseWithSpecificBody(getRefundsJsonConvertedResponse));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.updateRefundByGuid(guid, status);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).update("refunds/guid/" + guid, requestedJson);
        Assertions.assertEquals(guid, result.getGuid());
    }

    @Test
    public void it_should_test_sendRefundNotification() throws BitPayApiException, BitPayGenericException {
        // given
        final String refundId = "WoE46gSLkJQS48RJEiNw3L";
        final String refundToken = "cM78LHk17Q8fktDE6QLBBFfvH1QKBhRkHibTLcxhgzsu3VDRvSyu3CGi17DuwYxhT";
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("refundNotificationResponse.json");

        final String requestedJson = String.format("{\"token\":\"%s\"}", refundToken);

        Mockito.when(this.bitPayClient.post(
            "refunds/" + refundId + "/notifications",
            requestedJson,
            true
        )).thenReturn(this.getHttpResponseWithSpecificBody(getRefundsJsonConvertedResponse));
        Client testedClass = this.getTestedClass();

        // when
        Boolean result = testedClass.sendRefundNotification(refundId, refundToken);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "refunds/" + refundId + "/notifications",
            requestedJson,
            true
        );
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_cancel_refund_by_id() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String refundId = "WoE46gSLkJQS48RJEiNw3L";
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("getRefund.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));

        Mockito.when(this.bitPayClient.delete(
            "refunds/" + refundId,
            params
        )).thenReturn(this.getHttpResponseWithSpecificBody(getRefundsJsonConvertedResponse));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);

        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.cancelRefund(refundId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("refunds/" + refundId, params);
        Assertions.assertEquals(refundId, result.getId());
    }

    @Test
    public void it_should_cancel_refund_by_guid() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "merchantToken";
        final String guid = EXAMPLE_UUID;
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("getRefund.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));

        Mockito.when(this.bitPayClient.delete(
            "refunds/guid/" + guid,
            params
        )).thenReturn(this.getHttpResponseWithSpecificBody(getRefundsJsonConvertedResponse));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);

        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.cancelRefundByGuid(guid);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("refunds/guid/" + guid, params);
        Assertions.assertEquals(guid, result.getGuid());
    }

    @Test
    public void it_should_test_createBill_by_merchant_facade() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";
        final String createBillApiRequest = getPreparedJsonDataFromFile("createBillRequest.json");
        final Bill bill = getBillExample(merchantToken);

        Mockito.when(this.bitPayClient.post(
            "bills",
            createBillApiRequest,
            true
        )).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("createBillResponse.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(true);

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.createBill(bill);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("bills", createBillApiRequest, true);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_createBill_by_pos_facade() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";
        final String createBillApiRequest = getPreparedJsonDataFromFile("createBillRequest.json");
        final Bill bill = getBillExample(merchantToken);

        Mockito.when(this.bitPayClient.post(
            "bills",
            createBillApiRequest,
            false
        )).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("createBillPosResponse.json")));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(false);
        Mockito.when(this.accessTokens.getAccessToken(Facade.POS)).thenReturn(merchantToken);

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.createBill(bill);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.POS);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("bills", createBillApiRequest, false);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_getBill_by_merchant_facade() throws BitPayApiException, BitPayGenericException {
        // given
        final String facadeToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", facadeToken));

        Mockito.when(this.bitPayClient.get("bills/" + BILL_ID, params, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getBill.json")));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(true);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(facadeToken);

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.getBill(BILL_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("bills/" + BILL_ID, params, true);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_getBill_by_pos_facade() throws BitPayApiException, BitPayGenericException {
        // given
        final String facadeToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", facadeToken));

        Mockito.when(this.bitPayClient.get("bills/" + BILL_ID, params, false))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getBill.json")));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(false);
        Mockito.when(this.accessTokens.getAccessToken(Facade.POS)).thenReturn(facadeToken);

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.getBill(BILL_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).tokenExists(Facade.MERCHANT);
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.POS);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("bills/" + BILL_ID, params, false);
        Mockito.verify(this.bitPayClient, Mockito.times(0)).get("bills/" + BILL_ID, params, true);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_getBills() throws BitPayApiException, BitPayGenericException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));

        Mockito.when(this.bitPayClient.get(
            "bills",
            params
        )).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getBills.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);

        Client testedClass = this.getTestedClass();

        // when
        List<Bill> result = testedClass.getBills();

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("bills", params);
        Assertions.assertEquals(BILL_ID, result.get(0).getId());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void it_should_test_getBills_by_status() throws BitPayApiException, BitPayGenericException {
        // given
        final String status = "complete";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));
        params.add(new BasicNameValuePair("status", status));

        Mockito.when(this.bitPayClient.get(
            "bills",
            params
        )).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getBills.json")));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);

        Client testedClass = this.getTestedClass();

        // when
        List<Bill> result = testedClass.getBills(status);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("bills", params);
        Assertions.assertEquals(BILL_ID, result.get(0).getId());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void it_should_test_updateBill() throws BitPayApiException, BitPayGenericException {
        // given
        final String merchantToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";
        final Bill bill = this.getBillExample(merchantToken);
        final String updateBillApiRequest = getPreparedJsonDataFromFile("updateBillRequest.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));

        Mockito.when(this.bitPayClient.update(
            "bills/" + BILL_ID,
            updateBillApiRequest
        )).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getBill.json")));

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.updateBill(bill, BILL_ID);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).update("bills/" + BILL_ID, updateBillApiRequest);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_deliverBill_by_merchant_facade() throws BitPayApiException, BitPayGenericException {
        // given
        final String billToken = "billToken";
        Mockito.when(this.bitPayClient.post(
            "bills/" + BILL_ID + "/deliveries",
            "{\"token\":\"billToken\"}",
            true
        )).thenReturn(this.getHttpResponseWithSpecificBody("{\"data\": \"Success\"}"));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(true);

        Client testedClass = this.getTestedClass();

        // when
        String result = testedClass.deliverBill(BILL_ID, billToken);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "bills/" + BILL_ID + "/deliveries",
            "{\"token\":\"billToken\"}",
            true
        );
        Assertions.assertEquals("Success", result);
    }

    @Test
    public void it_should_test_deliverBill_by_pos_facade() throws BitPayApiException, BitPayGenericException {
        // given
        final String billToken = "billToken";
        Mockito.when(this.bitPayClient.post(
            "bills/" + BILL_ID + "/deliveries",
            "{\"token\":\"billToken\"}",
            false
        )).thenReturn(this.getHttpResponseWithSpecificBody("{\"data\": \"Success\"}"));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(false);

        Client testedClass = this.getTestedClass();

        // when
        String result = testedClass.deliverBill(BILL_ID, billToken);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "bills/" + BILL_ID + "/deliveries",
            "{\"token\":\"billToken\"}",
            false
        );
        Assertions.assertEquals("Success", result);
    }

    @Test
    public void it_should_return_rate() throws BitPayApiException, BitPayGenericException {
        // given
        Mockito.when(this.bitPayClient.get("rates/BCH/USD"))
            .thenReturn(this.getHttpResponseWithSpecificBody("{\"data\": {\"code\": \"USD\",\"name\": \"US Dollar\"," +
                    "\"rate\": 100.99 }}"));
        Client testedClass = this.getTestedClass();

        // when
        Rate result = testedClass.getRate("BCH", "USD");

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("rates/BCH/USD");
        Assertions.assertEquals(100.99, result.getValue());
    }

    @Test
    public void it_should_test_get_rates() throws BitPayApiException, BitPayGenericException {
        // given
        Mockito.when(this.bitPayClient.get("rates"))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getRates.json")));

        Client testedClass = this.getTestedClass();

        // when
        Rates result = testedClass.getRates();

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("rates");
        Assertions.assertEquals(41248.11, result.getRate("USD"));
    }

    @Test
    public void it_should_get_rates_by_base_currency() throws BitPayApiException, BitPayGenericException {
        // given
        Mockito.when(this.bitPayClient.get("rates/USD")).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getRates.json")));

        Client testedClass = this.getTestedClass();

        // when
        Rates result = testedClass.getRates("USD");

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("rates/USD");
        Assertions.assertEquals(41248.11, result.getRate("USD"));
    }

    @Test
    public void it_should_get_ledger_entries() throws BitPayApiException, BitPayGenericException {
        // given
        final String currency = "USD";
        final String dateStart = "2021-5-10";
        final String dateEnd = "2021-5-31";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));
        params.add(new BasicNameValuePair("startDate", dateStart));
        params.add(new BasicNameValuePair("endDate", dateEnd));

        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);
        Mockito.when(this.bitPayClient.get("ledgers/" + currency, params))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getLedgers.json")));

        Client testedClass = this.getTestedClass();

        // when
        List<LedgerEntry> result = testedClass.getLedgerEntries(currency, dateStart, dateEnd);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("ledgers/" + currency, params);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("20210510_fghij", result.get(0).getDescription());
    }

    @Test
    public void it_should_test_getLedgers() throws BitPayApiException, BitPayGenericException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);
        Mockito.when(this.bitPayClient.get("ledgers", params))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getLedgerBalances.json")));

        Client testedClass = this.getTestedClass();

        // when
        List<Ledger> result = testedClass.getLedgers();

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("ledgers", params);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(2389.82, result.get(1).getBalance());
    }

    @Test
    public void it_should_test_submitPayoutRecipients() throws BitPayApiException, BitPayGenericException {
        // given
        PayoutRecipients recipients = getPayoutRecipientsExample();
        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post(
            "recipients",
            getPreparedJsonDataFromFile("submitPayoutRecipientsRequest.json"),
            true
        )).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("submitPayoutRecipientsResponse.json")));

        Client testedClass = this.getTestedClass();

        // when
        List<PayoutRecipient> result = testedClass.submitPayoutRecipients(recipients);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "recipients",
            getPreparedJsonDataFromFile("submitPayoutRecipientsRequest.json"),
            true
        );
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(
            RECIPIENT_TOKEN,
            result.get(0).getToken()
        );
    }

    @Test
    public void it_should_test_getPayoutRecipients() throws BitPayApiException, BitPayGenericException {
        // given
        final String status = "invited";
        final Integer limit = 1;
        final Integer offset = 0;
        PayoutRecipients recipients = getPayoutRecipientsExample();
        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));
        params.add(new BasicNameValuePair("status", status));
        params.add(new BasicNameValuePair("limit", limit.toString()));
        params.add(new BasicNameValuePair("offset", offset.toString()));

        Mockito.when(this.bitPayClient.get("recipients", params, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("retrieveRecipientsResponse.json")));

        Client testedClass = this.getTestedClass();

        // when
        List<PayoutRecipient> result = testedClass.getPayoutRecipients(status, limit, offset);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("recipients", params, true);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(
            RECIPIENT_TOKEN,
            result.get(0).getToken()
        );
    }

    @Test
    public void it_should_test_getPayoutRecipient() throws BitPayApiException, BitPayGenericException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.get("recipients/" + RECIPIENT_ID, params, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("retrieveRecipientResponse.json")));

        Client testedClass = this.getTestedClass();

        // when

        PayoutRecipient result = testedClass.getPayoutRecipient(RECIPIENT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("recipients/" + RECIPIENT_ID, params, true);
        Assertions.assertEquals(
            RECIPIENT_TOKEN,
            result.getToken()
        );
    }

    @Test
    public void it_should_test_updatePayoutRecipient() throws BitPayApiException, BitPayGenericException {
        // given
        final PayoutRecipient payoutRecipient = new PayoutRecipient();
        final String label = "Bob123";
        payoutRecipient.setLabel(label);
        payoutRecipient.setToken(PAYOUT_ACCESS_TOKEN);

        Mockito.when(this.guidGenerator.execute()).thenReturn(EXAMPLE_UUID);
        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.update(
            "recipients/" + RECIPIENT_ID,
            getPreparedJsonDataFromFile("updatePayoutRecipientRequest.json")
        )).thenReturn(
            this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("retrieveRecipientResponse.json")));

        Client testedClass = this.getTestedClass();

        // when

        PayoutRecipient result = testedClass.updatePayoutRecipient(RECIPIENT_ID, payoutRecipient);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).update(
            "recipients/" + RECIPIENT_ID,
            getPreparedJsonDataFromFile("updatePayoutRecipientRequest.json")
        );
        Assertions.assertEquals(RECIPIENT_TOKEN, result.getToken());
        Assertions.assertEquals(label, result.getLabel());
    }

    @Test
    public void it_should_test_deletePayoutRecipient() throws BitPayApiException, BitPayGenericException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.delete("recipients/" + RECIPIENT_ID, params))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("success.json")));

        Client testedClass = this.getTestedClass();

        // when
        Boolean result = testedClass.deletePayoutRecipient(RECIPIENT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete(
            "recipients/" + RECIPIENT_ID,
            params
        );
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_requestPayoutRecipientNotification() throws BitPayApiException, BitPayGenericException {
        // given
        final String requestJson = "{\"token\":\"3LKKrrNB2BcVAu2Y24QQ78GrKUk2ANLK4eLo85Q1a2HU\"}";

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post(
            "recipients/" + RECIPIENT_ID + "/notifications",
            requestJson,
            true
        )).thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("success.json")));

        Client testedClass = this.getTestedClass();

        // when

        Boolean result = testedClass.requestPayoutRecipientNotification(RECIPIENT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "recipients/" + RECIPIENT_ID + "/notifications",
            requestJson,
            true
        );
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_submitPayout() throws BitPayApiException, BitPayGenericException {
        // given
        final String requestJson = getPreparedJsonDataFromFile("submitPayoutRequest.json");
        Payout payout = getPayoutExample();

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post("payouts", requestJson, true))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("submitPayoutResponse.json")));

        Client testedClass = this.getTestedClass();

        // when

        Payout result = testedClass.submitPayout(payout);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("payouts", requestJson, true);
        Assertions.assertEquals(
            "6RZSTPtnzEaroAe2X4YijenRiqteRDNvzbT8NjtcHjUVd9FUFwa7dsX8RFgRDDC5SL",
            result.getToken()
        );
    }

    @Test
    public void it_should_test_submitPayoutGroup() throws BitPayApiException, BitPayGenericException, ParseException {
        // given
        final String requestJson = getPreparedJsonDataFromFile("submitPayoutGroupRequest.json");
        Collection<Payout> payouts = Arrays.asList(getPayoutExample(), getPayoutExample());

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post("payouts/group", requestJson, true))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("submitPayoutGroupResponse.json")));

        Client testedClass = this.getTestedClass();

        // when

        PayoutGroup result = testedClass.submitPayouts(payouts);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("payouts/group", requestJson, true);

        Assertions.assertFalse(result.getPayouts().isEmpty());
        Payout firstPayout = result.getPayouts().get(0);

        Assertions.assertEquals(10.0, firstPayout.getAmount());
        Assertions.assertEquals("USD", firstPayout.getCurrency());
        Assertions.assertNull(firstPayout.getDateExecuted());
        Assertions.assertEquals(
            "2021-05-27T09:00Z",
            firstPayout.getEffectiveDate().toString()
        );
        Assertions.assertEquals("john@doe.com", firstPayout.getEmail());
        Assertions.assertNull(firstPayout.getExchangeRates());
        Assertions.assertEquals("JMwv8wQCXANoU2ZZQ9a9GH", firstPayout.getId());
        Assertions.assertEquals("John Doe", firstPayout.getLabel());
        Assertions.assertEquals("GBP", firstPayout.getLedgerCurrency());
        Assertions.assertNull(firstPayout.getMessage());
        Assertions.assertEquals("merchant@email.com", firstPayout.getNotificationEmail());
        Assertions.assertEquals(
            "https://yournotiticationURL.com/wed3sa0wx1rz5bg0bv97851eqx",
            firstPayout.getNotificationUrl()
        );
        Assertions.assertEquals("LDxRZCGq174SF8AnQpdBPB", firstPayout.getRecipientId());
        Assertions.assertEquals("payout_20210527", firstPayout.getReference());
        Assertions.assertEquals(
            "2021-05-27T10:47:37.834Z",
            firstPayout.getRequestDate().toString()
        );
        Assertions.assertEquals("7qohDf2zZnQK5Qanj8oyC2", firstPayout.getShopperId());
        Assertions.assertEquals("new", firstPayout.getStatus());
        Assertions.assertTrue(firstPayout.getTransactions().isEmpty());

        Assertions.assertFalse(result.getFailed().isEmpty());
        PayoutGroupFailed failed = result.getFailed().get(0);
        Assertions.assertEquals("Ledger currency is required", failed.getErrMessage());
        Assertions.assertEquals("john@doe.com", failed.getPayee());
    }

    @Test
    public void it_should_test_getPayout() throws BitPayApiException, BitPayGenericException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.get("payouts/" + PAYOUT_ID, params, true))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("submitPayoutResponse.json")));

        Client testedClass = this.getTestedClass();

        // when

        Payout result = testedClass.getPayout(PAYOUT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get(
            "payouts/" + PAYOUT_ID, params, true
        );
        Assertions.assertEquals(PAYOUT_ID, result.getId());
    }

    @Test
    public void it_should_test_cancelPayout() throws BitPayApiException, BitPayGenericException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.delete("payouts/" + PAYOUT_ID, params))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("success.json")));

        Client testedClass = this.getTestedClass();

        // when

        Boolean result = testedClass.cancelPayout(PAYOUT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("payouts/" + PAYOUT_ID, params);
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_cancelPayoutGroup() throws BitPayApiException, BitPayGenericException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.delete("payouts/group/" + PAYOUT_ID, params))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("cancelPayoutGroupResponse.json")));
        Client testedClass = this.getTestedClass();

        // when

        PayoutGroup result = testedClass.cancelPayouts(PAYOUT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("payouts/group/" + PAYOUT_ID, params);

        Assertions.assertFalse(result.getPayouts().isEmpty());
        Assertions.assertFalse(result.getFailed().isEmpty());

        PayoutGroupFailed failed = result.getFailed().get(0);
        Assertions.assertEquals("D8tgWzn1psUua4NYWW1vYo", failed.getPayoutId());
        Assertions.assertEquals("PayoutId is missing or invalid", failed.getErrMessage());
    }

    @Test
    public void it_should_test_getPayouts() throws BitPayApiException, BitPayGenericException {
        // given
        final String startDate = "2021-05-27";
        final String endDate = "2021-05-31";
        final Integer limit = 10;
        final Integer offset = 1;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));
        params.add(new BasicNameValuePair("startDate", startDate));
        params.add(new BasicNameValuePair("endDate", endDate));
        params.add(new BasicNameValuePair("limit", limit.toString()));
        params.add(new BasicNameValuePair("offset", offset.toString()));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.get("payouts", params, true))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getPayouts.json")));

        Client testedClass = this.getTestedClass();

        // when
        List<Payout> result = testedClass.getPayouts(
            startDate,
            endDate,
            null,
            null,
            limit,
            offset,
            null
        );

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("payouts", params, true);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void it_should_test_requestPayoutNotification() throws BitPayApiException, BitPayGenericException {
        // given
        final String requestJson = "{\"token\":\"3LKKrrNB2BcVAu2Y24QQ78GrKUk2ANLK4eLo85Q1a2HU\"}";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post("payouts/" + PAYOUT_ID + "/notifications", requestJson, true))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("success.json")));

        Client testedClass = this.getTestedClass();

        // when
        Boolean result = testedClass.requestPayoutNotification(PAYOUT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "payouts/" + PAYOUT_ID + "/notifications",
            requestJson,
            true
        );
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_getSettlements() throws BitPayApiException, BitPayGenericException {
        // given
        final String currency = "USD";
        final String dateStart = "2021-5-10";
        final String dateEnd = "2021-5-12";
        final String status = "processing";
        final Integer limit = 10;
        final Integer offset = 0;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));
        params.add(new BasicNameValuePair("startDate", dateStart));
        params.add(new BasicNameValuePair("endDate", dateEnd));
        params.add(new BasicNameValuePair("currency", currency));
        params.add(new BasicNameValuePair("status", status));
        params.add(new BasicNameValuePair("limit", limit.toString()));
        params.add(new BasicNameValuePair("offset", offset.toString()));

        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);
        Mockito.when(this.bitPayClient.get("settlements", params))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getSettlementsResponse.json")));

        Client testedClass = this.getTestedClass();

        // when
        List<Settlement> result = testedClass.getSettlements(
            currency,
            dateStart,
            dateEnd,
            status,
            limit,
            offset
        );

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("settlements", params);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("KBkdURgmE3Lsy9VTnavZHX", result.get(0).getId());
        Assertions.assertEquals("2021-05-10T09:05:00.176Z", result.get(0).getDateCreated().toString());
        Assertions.assertEquals("Z", result.get(0).getDateCreated().getZone().toString());
    }

    @Test
    public void it_should_test_getSettlement() throws BitPayApiException, BitPayGenericException {
        // given
        final String settlementId = "DNFnN3fFjjzLn6if5bdGJC";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);
        Mockito.when(this.bitPayClient.get("settlements/" + settlementId, params))
            .thenReturn(this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getSettlementResponse.json")));

        Client testedClass = this.getTestedClass();

        // when
        Settlement result = testedClass.getSettlement(settlementId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("settlements/" + settlementId, params);
        Assertions.assertEquals("RPWTabW8urd3xWv2To989v", result.getId());
    }

    @Test
    public void it_should_test_getSettlementReconciliationReport() throws BitPayApiException, BitPayGenericException {
        // given
        final String settlementId = "DNFnN3fFjjzLn6if5bdGJC";
        final String settlementToken = "5T1T5yGDEtFDYe8jEVBSYLHKewPYXZrDLvZxtXBzn69fBbZYitYQYH4BFYFvvaVU7D";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", settlementToken));

        Mockito.when(this.bitPayClient.get("settlements/" + settlementId + "/reconciliationreport", params))
            .thenReturn(this.getHttpResponseWithSpecificBody(
                getPreparedJsonDataFromFile("getSettlementReconciliationReportResponse.json")));

        Client testedClass = this.getTestedClass();

        // when
        Settlement result = testedClass.getSettlementReconciliationReport(settlementId, settlementToken);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1))
            .get("settlements/" + settlementId + "/reconciliationreport", params);
        Assertions.assertEquals("RvNuCTMAkURKimwgvSVEMP", result.getId());
        Assertions.assertEquals(2389.82F, result.getTotalAmount());
    }

    @Test
    public void it_should_test_getSupportedWallets() throws BitPayApiException, BitPayGenericException {
        // given
        Mockito.when(this.bitPayClient.get("supportedwallets"))
            .thenReturn(
                this.getHttpResponseWithSpecificBody(getPreparedJsonDataFromFile("getSupportedWalletsResponse.json")));

        Client testedClass = this.getTestedClass();

        // when
        List<Wallet> result = testedClass.getSupportedWallets();

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("supportedwallets");
        Assertions.assertEquals(7, result.size());
        Assertions.assertEquals("bitpay-wallet.png", result.get(0).getAvatar());
        Assertions.assertEquals("https://bitpay.com/img/wallet-logos/bitpay-wallet.png", result.get(0).getImage());
    }

    @Test
    public void it_should_get_http_client() {
        Client client = this.getTestedClass();
        final HttpHost httpHost = new HttpHost("http://localhost");

        Assertions.assertNotNull(client.getHttpClient(null, null));
        Assertions.assertNotNull(client.getHttpClient(httpHost, null));
        Assertions.assertNotNull(
            client.getHttpClient(httpHost,
                Mockito.mock(CredentialsProvider.class)
            )
        );
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

    private Bill getBillExample(String merchantToken) throws BitPayGenericException {
        List<String> cc = new ArrayList<String>();
        cc.add("jane@doe.com");

        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setDescription("Test Item 1");
        item1.setPrice(6.00);
        item1.setQuantity(1);
        item2.setDescription("Test Item 2");
        item2.setPrice(4.00);
        item2.setQuantity(1);
        items.add(item1);
        items.add(item2);

        final Bill bill = new Bill();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2021-05-21T09:48:02.373Z", formatter);
        bill.setToken(merchantToken);
        bill.setNumber("bill1234-ABCD");
        bill.setCurrency("USD");
        bill.setName("John Doe");
        bill.setAddress1("2630 Hegal Place");
        bill.setAddress2("Apt 42");
        bill.setCity("Alexandria");
        bill.setState("VA");
        bill.setZip("23242");
        bill.setCountry("US");
        bill.setEmail("23242");
        bill.setCc(cc);
        bill.setPhone("555-123-456");
        bill.setDueDate(zonedDateTime);
        bill.setPassProcessingFee(true);
        bill.setItems(items);
        bill.setToken(merchantToken);

        return bill;
    }

    private PayoutRecipients getPayoutRecipientsExample() {
        PayoutRecipient recipient1 = new PayoutRecipient();
        PayoutRecipient recipient2 = new PayoutRecipient();
        recipient1.setEmail("alice@email.com");
        recipient1.setLabel("Alice");
        recipient2.setEmail("bob@email.com");
        recipient2.setLabel("Bob");
        List<PayoutRecipient> recipients = new ArrayList<>();
        recipients.add(recipient1);
        recipients.add(recipient2);

        return new PayoutRecipients(recipients);
    }

    private Payout getPayoutExample() throws BitPayGenericException {
        final Payout payout = new Payout();
        payout.setAmount(10.00);
        payout.setCurrency("USD");
        payout.setLedgerCurrency("GBP");
        payout.setReference("payout_20210527");
        payout.setNotificationEmail("merchant@email.com");
        payout.setNotificationUrl("https://yournotiticationURL.com/wed3sa0wx1rz5bg0bv97851eqx");
        payout.setEmail("john@doe.com");
        payout.setLabel("John Doe");
        payout.setToken(PAYOUT_TOKEN);

        return payout;
    }

    /**
     * @param fileName filename of json
     * @return json response
     */
    protected String getPreparedJsonDataFromFile(String fileName) {
        final String pathname = "src/test/java/com/bitpay/sdk/json/" + fileName;
        File file = new File(pathname);

        String data = null;
        try {
            data = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            return "";
        }

        return data;
    }

    protected HttpResponse getHttpResponseWithSpecificBody(String body) {
        return new HttpResponse(200, body, new HashMap<>(), "en_US", "HTTP/1.1");
    }

    private Client getTestedClass() {
        return new Client(
            this.bitPayClient,
            IDENTITY,
            this.accessTokens,
            this.guidGenerator
        );
    }
}
