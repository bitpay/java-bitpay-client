/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

import com.bitpay.sdk.client.BitPayClient;
import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.RefundCreationException;
import com.bitpay.sdk.exceptions.RefundQueryException;
import com.bitpay.sdk.exceptions.RefundUpdateException;
import com.bitpay.sdk.model.Bill.Bill;
import com.bitpay.sdk.model.Bill.Item;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Invoice.Buyer;
import com.bitpay.sdk.model.Invoice.Invoice;
import com.bitpay.sdk.model.Invoice.Refund;
import com.bitpay.sdk.model.Ledger.Ledger;
import com.bitpay.sdk.model.Payout.Payout;
import com.bitpay.sdk.model.Payout.PayoutRecipient;
import com.bitpay.sdk.model.Payout.PayoutRecipients;
import com.bitpay.sdk.model.Rate.Rates;
import com.bitpay.sdk.model.Settlement.Settlement;
import com.bitpay.sdk.model.Wallet.Wallet;
import com.bitpay.sdk.util.AccessTokens;
import com.bitpay.sdk.util.GuidGenerator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Assertions;
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
    private AccessTokens accessTokens;
    @Mock
    private GuidGenerator guidGenerator;
    @Mock
    private HttpResponse httpResponse;
    @Mock
    private HttpEntity httpEntity;

    @Test
    public void it_should_provide_pos_client() throws BitPayException {
        // given
        String posToken = "posToken";

        // when
        Client bitpay = Client.createPosClient(new PosToken(posToken));

        // then
        Assertions.assertEquals(posToken, bitpay.getAccessToken(Facade.POS));
    }

    @Test
    public void it_should_provide_client_by_key() throws BitPayException {
        // given
        String privateKey =
            "3082013102010104208ae30afbc7e93cb10cb983f70863e546b53f0b2c6158b1a71b576fd09790cff3a081e33081e0020101302c06072a8648ce3d0101022100fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f3044042000000000000000000000000000000000000000000000000000000000000000000420000000000000000000000000000000000000000000000000000000000000000704410479be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8022100fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141020101a124032200035d6a7e38d7c08b8a626e2390d0360a72a58bd1c5e1348e0eb810d4bbab3d3adf";
        String merchantToken = "merchantToken";
        AccessTokens tokens = new AccessTokens();
        tokens.addMerchant(merchantToken);

        // when
        Client bitpay = Client.createClient(new PrivateKey(privateKey), tokens);

        // then
        Assertions.assertEquals(merchantToken, bitpay.getAccessToken(Facade.MERCHANT));
    }

    @Test
    public void it_should_provide_client_by_config() throws BitPayException {
        // given
        String path = System.getProperty("user.dir") + "/src/test/java/com/bitpay/sdk/BitPay.config.json";

        // when
        Client bitpay = Client.createClient(new ConfigFilePath(path));

        // then
        Assertions.assertEquals("merchantToken", bitpay.getAccessToken(Facade.MERCHANT));
        Assertions.assertEquals("payoutToken", bitpay.getAccessToken(Facade.PAYOUT));
    }

    @Test
    public void it_should_throws_bitpayexception_for_invalid_privateKey() throws BitPayException {
        BitPayException exception = Assertions.assertThrows(BitPayException.class, () -> {
            new Client(
                Environment.TEST,
                new PrivateKey("invalid"),
                Mockito.mock(AccessTokens.class),
                new HttpHost("http://localhost"),
                Mockito.mock(CredentialsProvider.class)
            );
        });

        Assertions.assertEquals(
            "Status: 000000 -> Reason: failed to deserialize BitPay server response (Config) : Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Private Key file not found",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_authorize_client_by_pairing_code() throws BitPayException {
        // given
        String pairingCode = "123123123";
        Mockito.when(this.guidGenerator.execute()).thenReturn(EXAMPLE_UUID);
        Mockito.when(this.bitPayClient.post("tokens",
            "{\"guid\":\"37bd36bd-6fcb-409c-a907-47f9244302aa\",\"id\":\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\",\"pairingCode\":\"123123123\"}"))
            .thenReturn(this.httpResponse);
        final String responseString =
            "[{\"policies\":[{\"policy\":\"id\",\"method\":\"active\",\"params\":[\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\"]}],\"token\":\"t0k3n\",\"facade\":\"merchant\",\"dateCreated\":1668425446554,\"pairingExpiration\":1668511846554,\"pairingCode\":\"123123123\"}]";
        Mockito.when(this.bitPayClient.responseToJsonString(httpResponse)).thenReturn(responseString);

        Client testedClass = this.getTestedClass();

        // when
        testedClass.authorizeClient(pairingCode);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Mockito.verify(this.accessTokens, Mockito.times(1)).put("merchant", "t0k3n");
    }

    @Test
    public void it_should_authorize_client_by_facade() throws BitPayException {
        // given
        Mockito.when(this.guidGenerator.execute()).thenReturn(EXAMPLE_UUID);
        Mockito.when(this.bitPayClient.post("tokens",
            "{\"count\":1,\"facade\":\"merchant\",\"guid\":\"37bd36bd-6fcb-409c-a907-47f9244302aa\",\"id\":\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\"}"))
            .thenReturn(this.httpResponse);
        final String responseString =
            "[{\"policies\":[{\"policy\":\"id\",\"method\":\"inactive\",\"params\":[\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\"]}],\"token\":\"G7XM9fcM1gtCN7DUr8ZWtPGVFLTKiYWanHR4kvqsnjP3\",\"facade\":\"merchant\",\"label\":\"merchantwebsite.com\",\"dateCreated\":1621340364865,\"pairingExpiration\":1621426764865,\"pairingCode\":\"C4Lg7oW\"}]";
        Mockito.when(this.bitPayClient.responseToJsonString(httpResponse)).thenReturn(responseString);

        Client testedClass = this.getTestedClass();

        // when
        testedClass.authorizeClient(Facade.MERCHANT);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Mockito.verify(this.accessTokens, Mockito.times(1))
            .put("merchant", "G7XM9fcM1gtCN7DUr8ZWtPGVFLTKiYWanHR4kvqsnjP3");
    }

    @Test
    public void it_should_test_requestClientAuthorization() throws BitPayException {
        // given
        String pairingCode = "123123123";
        Mockito.when(this.guidGenerator.execute()).thenReturn(EXAMPLE_UUID);
        Mockito.when(this.bitPayClient.post("tokens",
            "{\"guid\":\"37bd36bd-6fcb-409c-a907-47f9244302aa\",\"id\":\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\",\"pairingCode\":\"123123123\"}"))
            .thenReturn(this.httpResponse);
        final String responseString =
            "[{\"policies\":[{\"policy\":\"id\",\"method\":\"active\",\"params\":[\"Tf2yXsY49iFyDfxt3b2kf9VPRMwPxxAyCRW\"]}],\"token\":\"t0k3n\",\"facade\":\"merchant\",\"dateCreated\":1668425446554,\"pairingExpiration\":1668511846554,\"pairingCode\":\"\"}]";
        Mockito.when(this.bitPayClient.responseToJsonString(httpResponse)).thenReturn(responseString);

        Client testedClass = this.getTestedClass();

        // when
        testedClass.authorizeClient(pairingCode);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Mockito.verify(this.accessTokens, Mockito.times(1)).put("merchant", "t0k3n");
    }

    @Test
    public void it_should_test_getAccessToken() throws BitPayException {
        // given
        Client testedClass = this.getTestedClass();
        String tokenKey = "tokenKey";

        // when
        testedClass.getAccessToken(tokenKey);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(tokenKey);
    }

    @Test
    public void it_should_test_getCurrencyInfo() throws IOException {
        // given
        String response = getPreparedJsonDataFromFile("currencies.json");
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Mockito.when(this.bitPayClient.get("currencies")).thenReturn(this.httpResponse);
        Mockito.when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        Mockito.when(this.httpEntity.getContent()).thenReturn(inputStream);

        Client testedClass = this.getTestedClass();

        // when
        Map result = testedClass.getCurrencyInfo("USD");

        // then
        Assertions.assertEquals("USD", result.get("code"));
        Assertions.assertEquals("$", result.get("symbol"));
        Assertions.assertEquals("US Dollar", result.get("name"));
    }

    @Test
    public void it_should_test_createInvoice_by_merchant() throws IOException {
        // given
        Invoice invoice = getInvoiceExample();

        Mockito.when(this.bitPayClient.post("invoices", getPreparedJsonDataFromFile("createInvoiceRequest.json"), true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("createInvoiceResponse.json"));
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
    }

    @Test
    public void it_should_test_createInvoice_by_pos() throws IOException {
        // given
        Invoice invoice = getInvoiceExample();

        Mockito
            .when(this.bitPayClient.post("invoices", getPreparedJsonDataFromFile("createInvoiceRequest.json"), false))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("createInvoiceResponse.json"));
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
    public void it_should_test_getInvoice_by_merchant() throws BitPayException {
        // given
        String id = "UZjwcYkWAKfTMn9J1yyfs4";
        final String facadeToken = "someToken";
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", facadeToken));

        Mockito.when(this.bitPayClient
            .get(ArgumentMatchers.eq("invoices/" + id), ArgumentMatchers.eq(params), ArgumentMatchers.eq(true)))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getInvoice.json"));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(true);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT))
            .thenReturn(facadeToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.getInvoice(id);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Mockito.verify(this.bitPayClient, Mockito.times(1))
            .get(ArgumentMatchers.eq("invoices/" + id), ArgumentMatchers.eq(params), ArgumentMatchers.eq(true));
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_getInvoice_by_pos() throws BitPayException {
        // given
        String id = "UZjwcYkWAKfTMn9J1yyfs4";
        final String facadeToken = "someToken";
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", facadeToken));

        Mockito.when(this.bitPayClient
            .get(ArgumentMatchers.eq("invoices/" + id), ArgumentMatchers.eq(params), ArgumentMatchers.eq(false)))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getInvoice.json"));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(false);
        Mockito.when(this.accessTokens.getAccessToken(Facade.POS)).thenReturn(facadeToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.getInvoice(id);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Mockito.verify(this.bitPayClient, Mockito.times(1))
            .get(ArgumentMatchers.eq("invoices/" + id), ArgumentMatchers.eq(params), ArgumentMatchers.eq(false));
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_getInvoiceByGuid() throws BitPayException {
        // given
        String guid = "chc9kj52-04g0-4b6f-941d-3a844e352758";
        String merchantToken = "merchantToken";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));
        Mockito.when(this.bitPayClient
            .get(ArgumentMatchers.eq("invoices/guid/" + guid), ArgumentMatchers.eq(expectedParams),
                ArgumentMatchers.eq(true)))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getInvoice.json"));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.getInvoiceByGuid(guid, Facade.MERCHANT, true);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_getInvoices() throws BitPayException {
        // given
        String merchantToken = "merchantToken";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));
        expectedParams.add(new BasicNameValuePair("dateStart", "2022-5-10"));
        expectedParams.add(new BasicNameValuePair("dateEnd", "2022-5-11"));
        expectedParams.add(new BasicNameValuePair("status", "complete"));
        expectedParams.add(new BasicNameValuePair("limit", "1"));
        Mockito.when(this.bitPayClient.get(ArgumentMatchers.eq("invoices"), ArgumentMatchers.eq(expectedParams)))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getInvoices.json"));
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void it_should_test_throws_exception_for_missing_arguments_in_updateInvoice() throws BitPayException {
        Assertions.assertThrows(BitPayException.class, () -> {
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
    public void it_should_test_updateInvoice() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";

        Mockito.when(this.bitPayClient.update(ArgumentMatchers.eq("invoices/" + invoiceId),
            ArgumentMatchers.eq("{\"buyerSms\":\"+12223334444\",\"token\":\"merchantToken\"}")))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getInvoice.json"));
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_payInvoice() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        final String status = "complete";

        Mockito.when(this.bitPayClient.update(ArgumentMatchers.eq("invoices/pay/" + invoiceId),
            ArgumentMatchers.eq("{\"token\":\"merchantToken\",\"status\":\"complete\"}")))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getInvoice.json"));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.payInvoice(
            invoiceId,
            status
        );

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_force_cancelInvoice() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));
        expectedParams.add(new BasicNameValuePair("forceCancel", "true"));

        Mockito.when(this.bitPayClient.delete(
            ArgumentMatchers.eq("invoices/" + invoiceId), ArgumentMatchers.eq(expectedParams))
        ).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getInvoice.json"));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.cancelInvoice(invoiceId, true);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("invoices/" + invoiceId, expectedParams);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_test_cancelInvoice() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        List<BasicNameValuePair> expectedParams = new ArrayList<BasicNameValuePair>();
        expectedParams.add(new BasicNameValuePair("token", merchantToken));

        Mockito.when(this.bitPayClient.delete(
            ArgumentMatchers.eq("invoices/" + invoiceId), ArgumentMatchers.eq(expectedParams))
        ).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getInvoice.json"));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Invoice result = testedClass.cancelInvoice(invoiceId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("invoices/" + invoiceId, expectedParams);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("chc9kj52-04g0-4b6f-941d-3a844e352758", result.getGuid());
    }

    @Test
    public void it_should_create_refund() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "UZjwcYkWAKfTMn9J1yyfs4";
        final String createRefundJsonRequest = getPreparedJsonDataFromFile("createRefundRequest.json");

        Mockito.when(this.bitPayClient.post("refunds/", createRefundJsonRequest, true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("createRefundResponse.json"));
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(invoiceId, result.getInvoice());
        Assertions.assertEquals(EXAMPLE_UUID, result.getGuid());
    }

    @Test
    public void it_should_throws_refundCreationException_for_missing_invoice_id_and_amount_for_createRefund() {
        RefundCreationException exception = Assertions.assertThrows(
            RefundCreationException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();

                // when
                testedClass.createRefund(null, null, true, true, true, "no");
            }
        );

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-REFUND-CREATE: Failed to create refund -> Invoice ID, amount and currency are required to issue a refund.",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_refundCreationException_for_invalid_sendRefundNotification() {
        RefundCreationException exception = Assertions.assertThrows(
            RefundCreationException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                Mockito.when(this.bitPayClient.post(
                    ArgumentMatchers.eq("refunds/1/notifications"),
                    ArgumentMatchers.anyString(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayException("500", "error message"));

                // when
                testedClass.sendRefundNotification("1");
            }
        );

        Assertions.assertEquals(
            "Status: 500 -> Reason: BITPAY-REFUND-CREATE: Failed to create refund -> error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_refundCreationException_for_api_issue_for_createRefund() {
        RefundCreationException exception = Assertions.assertThrows(
            RefundCreationException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                Mockito.when(this.bitPayClient.post(
                    ArgumentMatchers.eq("refunds/"),
                    ArgumentMatchers.anyString(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayException("500", "error message"));

                // when
                testedClass.createRefund("123", 2.20, true, true, true, "no");
            }
        );

        Assertions.assertEquals(
            "Status: 500 -> Reason: BITPAY-REFUND-CREATE: Failed to create refund -> error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_refundQueryException_for_api_issue_for_getRefund() {
        RefundQueryException exception = Assertions.assertThrows(
            RefundQueryException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                String id = "12";
                Mockito.when(this.bitPayClient.get(
                    ArgumentMatchers.eq("refunds/" + id),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayException("500", "error message"));

                // when
                testedClass.getRefund(id);
            }
        );

        Assertions.assertEquals(
            "Status: 500 -> Reason: BITPAY-REFUND-GET: Failed to retrieve refund -> error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_refundQueryException_for_api_issue_for_getRefunds() {
        RefundQueryException exception = Assertions.assertThrows(
            RefundQueryException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                String id = "12";
                Mockito.when(this.bitPayClient.get(
                    ArgumentMatchers.eq("refunds/"),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayException("500", "error message"));

                // when
                testedClass.getRefunds(id);
            }
        );

        Assertions.assertEquals(
            "Status: 500 -> Reason: BITPAY-REFUND-GET: Failed to retrieve refund -> error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_refundUpdateException_for_api_issue_for_getRefunds() {
        RefundQueryException exception = Assertions.assertThrows(
            RefundQueryException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                String id = "12";
                Mockito.when(this.bitPayClient.get(
                    ArgumentMatchers.eq("refunds/"),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(true)
                )).thenThrow(new BitPayException("500", "error message"));

                // when
                testedClass.getRefunds(id);
            }
        );

        Assertions.assertEquals(
            "Status: 500 -> Reason: BITPAY-REFUND-GET: Failed to retrieve refund -> error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_refundUpdateException_for_api_issue_for_updateRefund() {
        RefundUpdateException exception = Assertions.assertThrows(
            RefundUpdateException.class,
            () -> {
                // given
                Client testedClass = this.getTestedClass();
                String id = "12";
                Mockito.when(this.bitPayClient.update(
                    ArgumentMatchers.eq("refunds/12"),
                    ArgumentMatchers.eq("{\"token\":null,\"status\":\"complete\"}")
                )).thenThrow(new BitPayException("500", "error message"));

                // when
                testedClass.updateRefund(id, "complete");
            }
        );

        Assertions.assertEquals(
            "Status: 500 -> Reason: BITPAY-REFUND-UPDATE: Failed to update refund -> error message",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_get_refund_by_id() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String refundId = "WoE46gSLkJQS48RJEiNw3L";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", "merchantToken"));

        Mockito.when(this.bitPayClient.get("refunds/" + refundId, params, true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getRefund.json"));
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(refundId, result.getId());
    }

    @Test
    public void it_should_get_refund_by_guid() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", "merchantToken"));

        Mockito.when(this.bitPayClient.get("refunds/guid/" + EXAMPLE_UUID, params, true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getRefund.json"));
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(EXAMPLE_UUID, result.getGuid());
    }

    @Test
    public void it_should_test_getRefunds() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String invoiceId = "Hpqc63wvE1ZjzeeH4kEycF";
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("getRefunds.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));
        params.add(new BasicNameValuePair("invoiceId", invoiceId));

        Mockito.when(this.bitPayClient.get("refunds/", params, true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getRefundsJsonConvertedResponse);
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(invoiceId, result.get(0).getInvoice());
    }

    @Test
    public void it_should_update_refund_by_id() throws BitPayException {
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
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getRefundsJsonConvertedResponse);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.updateRefund(refundId, status);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).update("refunds/" + refundId, requestedJson);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(refundId, result.getId());
    }

    @Test
    public void it_should_update_refund_by_guid() throws BitPayException {
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
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getRefundsJsonConvertedResponse);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.updateRefundByGuid(guid, status);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).update("refunds/guid/" + guid, requestedJson);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(guid, result.getGuid());
    }

    @Test
    public void it_should_test_sendRefundNotification() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String refundId = "WoE46gSLkJQS48RJEiNw3L";
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("refundNotificationResponse.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));
        final String requestedJson = "{\"token\":\"merchantToken\"}";

        Mockito.when(this.bitPayClient.post(
            "refunds/" + refundId + "/notifications",
            requestedJson,
            true
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getRefundsJsonConvertedResponse);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Client testedClass = this.getTestedClass();

        // when
        Boolean result = testedClass.sendRefundNotification(refundId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post(
            "refunds/" + refundId + "/notifications",
            requestedJson,
            true
        );
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_cancelRefund() throws BitPayException {
        // given
        final String merchantToken = "merchantToken";
        final String refundId = "WoE46gSLkJQS48RJEiNw3L";
        final String getRefundsJsonConvertedResponse = getPreparedJsonDataFromFile("getRefund.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));

        Mockito.when(this.bitPayClient.delete(
            "refunds/" + refundId,
            params
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getRefundsJsonConvertedResponse);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);

        Client testedClass = this.getTestedClass();

        // when
        Refund result = testedClass.cancelRefund(refundId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("refunds/" + refundId, params);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(refundId, result.getId());
    }

    @Test
    public void it_should_test_createBill_by_merchant_facade() throws BitPayException {
        // given
        final String merchantToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";
        final String createBillApiRequest = getPreparedJsonDataFromFile("createBillRequest.json");
        final Bill bill = getBillExample(merchantToken);

        Mockito.when(this.bitPayClient.post(
            "bills",
            createBillApiRequest,
            true
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("createBillResponse.json"));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(merchantToken);
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(true);

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.createBill(bill);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("bills", createBillApiRequest, true);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_createBill_by_pos_facade() throws BitPayException {
        // given
        final String merchantToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";
        final String createBillApiRequest = getPreparedJsonDataFromFile("createBillRequest.json");
        final Bill bill = getBillExample(merchantToken);

        Mockito.when(this.bitPayClient.post(
            "bills",
            createBillApiRequest,
            false
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("createBillPosResponse.json"));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(false);
        Mockito.when(this.accessTokens.getAccessToken(Facade.POS)).thenReturn(merchantToken);

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.createBill(bill);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.POS);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("bills", createBillApiRequest, false);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_getBill_by_merchant_facade() throws BitPayException {
        // given
        final String facadeToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", facadeToken));

        Mockito.when(this.bitPayClient.get("bills/" + BILL_ID, params, true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getBill.json"));
        Mockito.when(this.accessTokens.tokenExists(Facade.MERCHANT)).thenReturn(true);
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(facadeToken);

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.getBill(BILL_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("bills/" + BILL_ID, params, true);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_getBill_by_pos_facade() throws BitPayException {
        // given
        final String facadeToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", facadeToken));

        Mockito.when(this.bitPayClient.get("bills/" + BILL_ID, params, false))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getBill.json"));
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_getBills() throws BitPayException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));

        Mockito.when(this.bitPayClient.get(
            "bills",
            params
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getBills.json"));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);

        Client testedClass = this.getTestedClass();

        // when
        List<Bill> result = testedClass.getBills();

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("bills", params);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(BILL_ID, result.get(0).getId());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void it_should_test_getBills_by_status() throws BitPayException {
        // given
        final String status = "complete";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));
        params.add(new BasicNameValuePair("status", status));

        Mockito.when(this.bitPayClient.get(
            "bills",
            params
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getBills.json"));
        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);

        Client testedClass = this.getTestedClass();

        // when
        List<Bill> result = testedClass.getBills(status);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("bills", params);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(BILL_ID, result.get(0).getId());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void it_should_test_updateBill() throws BitPayException {
        // given
        final String merchantToken = "AKnJyeLF1BjAfgfDbVUzHXk64N1WuDq3R9xtZouQFhSv";
        final Bill bill = this.getBillExample(merchantToken);
        final String updateBillApiRequest = getPreparedJsonDataFromFile("updateBillRequest.json");

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", merchantToken));

        Mockito.when(this.bitPayClient.update(
            "bills/" + BILL_ID,
            updateBillApiRequest
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getBill.json"));

        Client testedClass = this.getTestedClass();

        // when
        Bill result = testedClass.updateBill(bill, BILL_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).put(BILL_ID, result.getToken());
        Mockito.verify(this.bitPayClient, Mockito.times(1)).update("bills/" + BILL_ID, updateBillApiRequest);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(BILL_ID, result.getId());
    }

    @Test
    public void it_should_test_deliverBill_by_merchant_facade() throws BitPayException {
        // given
        final String billToken = "billToken";
        Mockito.when(this.bitPayClient.post(
            "bills/" + BILL_ID + "/deliveries",
            "{\"token\":\"billToken\"}",
            true
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn("Success");
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("Success", result);
    }

    @Test
    public void it_should_test_deliverBill_by_pos_facade() throws BitPayException {
        // given
        final String billToken = "billToken";
        Mockito.when(this.bitPayClient.post(
            "bills/" + BILL_ID + "/deliveries",
            "{\"token\":\"billToken\"}",
            false
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn("Success");
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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("Success", result);
    }

    @Test
    public void it_should_test_getRates() throws BitPayException {
        // given
        Mockito.when(this.bitPayClient.get("rates")).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getRates.json"));

        Client testedClass = this.getTestedClass();

        // when
        Rates result = testedClass.getRates();

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("rates");
        Assertions.assertEquals(41248.11, result.getRate("USD"));
    }

    @Test
    public void it_should_test_getLedger() throws BitPayException {
        // given
        final String currency = "USD";
        final String dateStart = "2021-5-10";
        final String dateEnd = "2021-5-31";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));
        params.add(new BasicNameValuePair("startDate", dateStart));
        params.add(new BasicNameValuePair("endDate", dateEnd));

        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);
        Mockito.when(this.bitPayClient.get("ledgers/" + currency, params)).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getLedgers.json"));

        Client testedClass = this.getTestedClass();

        // when
        Ledger result = testedClass.getLedger(currency, dateStart, dateEnd);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("ledgers/" + currency, params);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(3, result.getEntries().size());
        Assertions.assertEquals("20210510_fghij", result.getEntries().get(0).getDescription());
    }

    @Test
    public void it_should_test_getLedgers() throws BitPayException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);
        Mockito.when(this.bitPayClient.get("ledgers", params)).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getLedgerBalances.json"));

        Client testedClass = this.getTestedClass();

        // when
        List<Ledger> result = testedClass.getLedgers();

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("ledgers", params);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(2389.82, result.get(1).getBalance());
    }

    @Test
    public void it_should_test_submitPayoutRecipients() throws BitPayException {
        // given
        PayoutRecipients recipients = getPayoutRecipientsExample();
        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post(
            "recipients",
            getPreparedJsonDataFromFile("submitPayoutRecipientsRequest.json"),
            true
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("submitPayoutRecipientsResponse.json"));

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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(
            RECIPIENT_TOKEN,
            result.get(0).getToken()
        );
    }

    @Test
    public void it_should_test_getPayoutRecipients() throws BitPayException {
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

        Mockito.when(this.bitPayClient.get("recipients", params, true)).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("retrieveRecipientsResponse.json"));

        Client testedClass = this.getTestedClass();

        // when
        List<PayoutRecipient> result = testedClass.getPayoutRecipients(status, limit, offset);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("recipients", params, true);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(
            RECIPIENT_TOKEN,
            result.get(0).getToken()
        );
    }

    @Test
    public void it_should_test_getPayoutRecipient() throws BitPayException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.get("recipients/" + RECIPIENT_ID, params, true)).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("retrieveRecipientResponse.json"));

        Client testedClass = this.getTestedClass();

        // when

        PayoutRecipient result = testedClass.getPayoutRecipient(RECIPIENT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("recipients/" + RECIPIENT_ID, params, true);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(
            RECIPIENT_TOKEN,
            result.getToken()
        );
    }

    @Test
    public void it_should_test_updatePayoutRecipient() throws BitPayException {
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
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("retrieveRecipientResponse.json"));

        Client testedClass = this.getTestedClass();

        // when

        PayoutRecipient result = testedClass.updatePayoutRecipient(RECIPIENT_ID, payoutRecipient);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).update(
            "recipients/" + RECIPIENT_ID,
            getPreparedJsonDataFromFile("updatePayoutRecipientRequest.json")
        );
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(RECIPIENT_TOKEN, result.getToken());
        Assertions.assertEquals(label, result.getLabel());
    }

    @Test
    public void it_should_test_deletePayoutRecipient() throws BitPayException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.delete("recipients/" + RECIPIENT_ID, params)).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("success.json"));

        Client testedClass = this.getTestedClass();

        // when
        Boolean result = testedClass.deletePayoutRecipient(RECIPIENT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete(
            "recipients/" + RECIPIENT_ID,
            params
        );
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_requestPayoutRecipientNotification() throws BitPayException {
        // given
        final String requestJson = "{\"token\":\"3LKKrrNB2BcVAu2Y24QQ78GrKUk2ANLK4eLo85Q1a2HU\"}";

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post(
            "recipients/" + RECIPIENT_ID + "/notifications",
            requestJson,
            true
        )).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("success.json"));

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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_submitPayout() throws BitPayException {
        // given
        final String requestJson = getPreparedJsonDataFromFile("submitPayoutRequest.json");
        Payout payout = getPayoutExample();

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post("payouts", requestJson, true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("submitPayoutResponse.json"));

        Client testedClass = this.getTestedClass();

        // when

        Payout result = testedClass.submitPayout(payout);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).post("payouts", requestJson, true);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(
            "6RZSTPtnzEaroAe2X4YijenRiqteRDNvzbT8NjtcHjUVd9FUFwa7dsX8RFgRDDC5SL",
            result.getToken()
        );
    }

    @Test
    public void it_should_test_getPayout() throws BitPayException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.get("payouts/" + PAYOUT_ID, params, true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("submitPayoutResponse.json"));

        Client testedClass = this.getTestedClass();

        // when

        Payout result = testedClass.getPayout(PAYOUT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get(
            "payouts/" + PAYOUT_ID, params, true
        );
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(PAYOUT_ID, result.getId());
    }

    @Test
    public void it_should_test_cancelPayout() throws BitPayException {
        // given
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.delete("payouts/" + PAYOUT_ID, params))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("success.json"));

        Client testedClass = this.getTestedClass();

        // when

        Boolean result = testedClass.cancelPayout(PAYOUT_ID);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).delete("payouts/" + PAYOUT_ID, params);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_getPayouts() throws BitPayException {
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
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getPayouts.json"));

        Client testedClass = this.getTestedClass();

        // when
        List<Payout> result = testedClass.getPayouts(startDate, endDate, null, null, limit, offset);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.PAYOUT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("payouts", params, true);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void it_should_test_requestPayoutNotification() throws BitPayException {
        // given
        final String requestJson = "{\"token\":\"3LKKrrNB2BcVAu2Y24QQ78GrKUk2ANLK4eLo85Q1a2HU\"}";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", PAYOUT_ACCESS_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.PAYOUT)).thenReturn(PAYOUT_ACCESS_TOKEN);
        Mockito.when(this.bitPayClient.post("payouts/" + PAYOUT_ID + "/notifications", requestJson, true))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("success.json"));

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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertTrue(result);
    }

    @Test
    public void it_should_test_getSettlements() throws BitPayException {
        // given
        final String currency = "USD";
        final String dateStart = "2021-5-10";
        final String dateEnd = "2021-5-12";
        final String status = "processing";
        final Integer limit = 10;
        final Integer offset = 0;

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));
        params.add(new BasicNameValuePair("dateStart", dateStart));
        params.add(new BasicNameValuePair("dateEnd", dateEnd));
        params.add(new BasicNameValuePair("currency", currency));
        params.add(new BasicNameValuePair("status", status));
        params.add(new BasicNameValuePair("limit", limit.toString()));
        params.add(new BasicNameValuePair("offset", offset.toString()));

        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);
        Mockito.when(this.bitPayClient.get("settlements", params))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getSettlementsResponse.json"));

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
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("KBkdURgmE3Lsy9VTnavZHX", result.get(0).getId());
    }

    @Test
    public void it_should_test_getSettlement() throws BitPayException {
        // given
        final String settlementId = "DNFnN3fFjjzLn6if5bdGJC";

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", MERCHANT_TOKEN));

        Mockito.when(this.accessTokens.getAccessToken(Facade.MERCHANT)).thenReturn(MERCHANT_TOKEN);
        Mockito.when(this.bitPayClient.get("settlements/" + settlementId, params))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getSettlementResponse.json"));

        Client testedClass = this.getTestedClass();

        // when
        Settlement result = testedClass.getSettlement(settlementId);

        // then
        Mockito.verify(this.accessTokens, Mockito.times(1)).getAccessToken(Facade.MERCHANT);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("settlements/" + settlementId, params);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("RPWTabW8urd3xWv2To989v", result.getId());
    }

    @Test
    public void it_should_test_getSettlementReconciliationReport() throws BitPayException {
        // given
        final String settlementId = "DNFnN3fFjjzLn6if5bdGJC";
        final String settlementToken = "5T1T5yGDEtFDYe8jEVBSYLHKewPYXZrDLvZxtXBzn69fBbZYitYQYH4BFYFvvaVU7D";
        Settlement settlement = new Settlement();
        settlement.setId(settlementId);
        settlement.setToken(settlementToken);

        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", settlementToken));

        Mockito.when(this.bitPayClient.get("settlements/" + settlementId + "/reconciliationreport", params))
            .thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getSettlementReconciliationReportResponse.json"));

        Client testedClass = this.getTestedClass();

        // when
        Settlement result = testedClass.getSettlementReconciliationReport(settlement);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1))
            .get("settlements/" + settlementId + "/reconciliationreport", params);
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
        Assertions.assertEquals("RvNuCTMAkURKimwgvSVEMP", result.getId());
        Assertions.assertEquals(2389.82F, result.getTotalAmount());
    }

    @Test
    public void it_should_test_getSupportedWallets() throws BitPayException {
        // given
        Mockito.when(this.bitPayClient.get("supportedwallets")).thenReturn(this.httpResponse);
        Mockito.when(this.bitPayClient.responseToJsonString(this.httpResponse))
            .thenReturn(getPreparedJsonDataFromFile("getSupportedWalletsResponse.json"));

        Client testedClass = this.getTestedClass();

        // when
        List<Wallet> result = testedClass.getSupportedWallets();

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).get("supportedwallets");
        Mockito.verify(this.bitPayClient, Mockito.times(1)).responseToJsonString(this.httpResponse);
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

    @Test
    public void it_should_test_setLoggerLevel() {
        // given
        Client testedClass = this.getTestedClass();

        // when
        testedClass.setLoggerLevel(3);

        // then
        Mockito.verify(this.bitPayClient, Mockito.times(1)).setLoggerLevel(3);
    }

    private Invoice getInvoiceExample() {
        Invoice invoice = new Invoice(10.0, "USD");
        invoice.setOrderId(EXAMPLE_UUID);
        invoice.setFullNotifications(true);
        invoice.setExtendedNotifications(true);
        invoice.setTransactionSpeed("medium");
        invoice.setNotificationURL("https://notification.url/aaa");
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

    private Bill getBillExample(String merchantToken) throws BitPayException {
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
        bill.setDueDate("2021-5-31");
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

    private Payout getPayoutExample() throws BitPayException {
        final Payout payout = new Payout();
        payout.setAmount(10.00);
        payout.setCurrency("USD");
        payout.setLedgerCurrency("GBP");
        payout.setReference("payout_20210527");
        payout.setNotificationEmail("merchant@email.com");
        payout.setNotificationURL("https://yournotiticationURL.com/wed3sa0wx1rz5bg0bv97851eqx");
        payout.setEmail("john@doe.com");
        payout.setLabel("John Doe");
        payout.setToken(PAYOUT_TOKEN);

        return payout;
    }

    /**
     * Be aware. I removed "data" index from original request
     *
     * @param fileName filename of json
     * @return json response
     */
    protected String getPreparedJsonDataFromFile(String fileName) {
        final String pathname = "src/test/java/com/bitpay/sdk/jsonResponse/" + fileName;
        File file = new File(pathname);

        String data = null;
        try {
            data = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            return "";
        }

        return data;
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
