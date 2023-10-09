/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.Config;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.bitcoinj.core.ECKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BitPayClientTest {

    protected static final String BASE_URL = "http://localhost";
    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpRequestFactory httpRequestFactory;

    @Test
    public void it_should_prepare_get_request_without_parameters()
        throws IOException, URISyntaxException {
        // given
        final String testUrl = "/test";
        final HttpGet httpGet = Mockito.mock(HttpGet.class);
        final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("key", "value"));

        Mockito.when(this.httpRequestFactory.createHttpGet(BASE_URL + testUrl)).thenReturn(httpGet);

        BitPayClient testedClass = this.getTestedClass();

        // when
        try {
            testedClass.get(testUrl, parameters);
        } catch (Exception e) {
            // missing response
        }

        // then
        Mockito.verify(httpClient, Mockito.times(1)).execute(httpGet);
        Mockito.verify(httpGet, Mockito.times(1)).setURI(new URI("http://localhost/test?key=value"));
        Mockito.verify(httpGet, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-signature"), ArgumentMatchers.anyString());
        Mockito.verify(httpGet, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-identity"), ArgumentMatchers.anyString());
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("x-accept-version", Config.BITPAY_API_VERSION);
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
        Mockito.verify(httpGet, Mockito.times(1))
            .addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);
    }

    @Test
    public void it_should_prepare_get_request_with_parameters()
        throws IOException, URISyntaxException {
        // given
        final String testUrl = "/test";
        final HttpGet httpGet = Mockito.mock(HttpGet.class);
        final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("key", "value"));

        Mockito.when(this.httpRequestFactory.createHttpGet(BASE_URL + testUrl)).thenReturn(httpGet);

        BitPayClient testedClass = this.getTestedClass();

        // when
        try {
            testedClass.get(testUrl, parameters, true);
        } catch (Exception e) {
            // missing response
        }

        // then
        Mockito.verify(httpClient, Mockito.times(1)).execute(httpGet);
        Mockito.verify(httpGet, Mockito.times(1)).setURI(new URI("http://localhost/test?key=value"));
        Mockito.verify(httpGet, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-signature"), ArgumentMatchers.anyString());
        Mockito.verify(httpGet, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-identity"), ArgumentMatchers.anyString());
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("x-accept-version", Config.BITPAY_API_VERSION);
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
        Mockito.verify(httpGet, Mockito.times(1))
            .addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);
    }

    @Test
    public void it_should_throws_bitpay_api_exception_when_something_is_wrong_for_get_requests() {
        BitPayApiException exception1 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpGet httpGet = Mockito.mock(HttpGet.class);
                Mockito.when(this.httpRequestFactory.createHttpGet(ArgumentMatchers.anyString())).thenReturn(httpGet);
                BitPayClient testedClass = this.getTestedClass();
                testedClass.get("[/]", new ArrayList<BasicNameValuePair>(), true);
            }
        );

        Assertions.assertEquals(
            "Illegal character in hostname at index 16: http://localhost[/]?",
            exception1.getMessage()
        );

        BitPayApiException exception2 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpGet httpGet = Mockito.mock(HttpGet.class);
                Mockito.when(this.httpRequestFactory.createHttpGet(ArgumentMatchers.anyString())).thenReturn(httpGet);
                Mockito.when(this.httpClient.execute(httpGet))
                    .thenThrow(new ClientProtocolException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.get("/test", new ArrayList<BasicNameValuePair>(), true);
            }
        );

        Assertions.assertEquals("Exception message", exception2.getMessage());


        BitPayApiException exception3 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpGet httpGet = Mockito.mock(HttpGet.class);
                Mockito.when(this.httpRequestFactory.createHttpGet(ArgumentMatchers.anyString())).thenReturn(httpGet);
                Mockito.when(this.httpClient.execute(httpGet)).thenThrow(new IOException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.get("/test", new ArrayList<BasicNameValuePair>(), true);
            },
            "Error: GET failed\n" + "Exception message"
        );

        Assertions.assertEquals("Exception message", exception3.getMessage());
    }

    @Test
    public void it_should_throws_bitpay_api_exception_when_something_is_wrong_for_delete_requests()
        throws IOException, URISyntaxException {
        BitPayApiException exception1 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpDelete httpDelete = Mockito.mock(HttpDelete.class);
                Mockito.when(this.httpRequestFactory.createHttpDelete(ArgumentMatchers.anyString()))
                    .thenReturn(httpDelete);
                BitPayClient testedClass = this.getTestedClass();
                testedClass.delete("[/]", new ArrayList<BasicNameValuePair>());
            }
        );

        Assertions.assertEquals(
                "Illegal character in hostname at index 16: http://localhost[/]?",
            exception1.getMessage()
        );

        BitPayApiException exception2 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpDelete httpDelete = Mockito.mock(HttpDelete.class);
                Mockito.when(this.httpRequestFactory.createHttpDelete(ArgumentMatchers.anyString()))
                    .thenReturn(httpDelete);
                Mockito.when(this.httpClient.execute(httpDelete))
                    .thenThrow(new ClientProtocolException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.delete("/test", new ArrayList<BasicNameValuePair>());
            }
        );

        Assertions.assertEquals(
            "Exception message",
            exception2.getMessage()
        );

        BitPayApiException exception3 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpDelete httpDelete = Mockito.mock(HttpDelete.class);
                Mockito.when(this.httpRequestFactory.createHttpDelete(ArgumentMatchers.anyString()))
                    .thenReturn(httpDelete);
                Mockito.when(this.httpClient.execute(httpDelete)).thenThrow(new IOException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.delete("/test", new ArrayList<BasicNameValuePair>());
            }
        );

        Assertions.assertEquals("Exception message", exception3.getMessage());
    }

    @Test
    public void it_should_throws_bitpay_api_exception_when_something_is_wrong_for_post_requests()
        throws IOException, URISyntaxException {
        BitPayApiException exception1 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpPost httpPost = Mockito.mock(HttpPost.class);
                Mockito.when(this.httpRequestFactory.createHttpPost(ArgumentMatchers.anyString()))
                    .thenReturn(httpPost);
                Mockito.when(this.httpClient.execute(httpPost))
                    .thenThrow(new ClientProtocolException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.post("/test", "{\"key\":\"value\"}");
            }
        );

        Assertions.assertEquals("Exception message", exception1.getMessage());


        BitPayApiException exception2 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpPost httpPost = Mockito.mock(HttpPost.class);
                Mockito.when(this.httpRequestFactory.createHttpPost(ArgumentMatchers.anyString()))
                    .thenReturn(httpPost);
                Mockito.when(this.httpClient.execute(httpPost)).thenThrow(new IOException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.post("/test", "{\"key\":\"value\"}");
            }
        );

        Assertions.assertEquals("Exception message", exception2.getMessage());
    }

    @Test
    public void it_should_throws_bitpay_api_exception_when_something_is_wrong_for_put_requests()
        throws IOException, URISyntaxException {
        BitPayApiException exception1 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpPut httpPut = Mockito.mock(HttpPut.class);
                Mockito.when(this.httpRequestFactory.createHttpPut(ArgumentMatchers.anyString()))
                    .thenReturn(httpPut);
                Mockito.when(this.httpClient.execute(httpPut))
                    .thenThrow(new ClientProtocolException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.update("[/]", "{\"key\":\"value\"}");
            }
        );

        Assertions.assertEquals("Exception message", exception1.getMessage());

        BitPayApiException exception2 = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                final HttpPut httpPut = Mockito.mock(HttpPut.class);
                Mockito.when(this.httpRequestFactory.createHttpPut(ArgumentMatchers.anyString()))
                    .thenReturn(httpPut);
                Mockito.when(this.httpClient.execute(httpPut)).thenThrow(new IOException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.update("[/]", "{\"key\":\"value\"}");
            }
        );

        Assertions.assertEquals("Exception message", exception2.getMessage());
    }

    @Test
    public void it_should_prepare_delete_request() throws IOException, URISyntaxException {
        // given
        final String testUrl = "/test";
        final HttpDelete httpDelete = Mockito.mock(HttpDelete.class);
        final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("key", "value"));

        Mockito.when(this.httpRequestFactory.createHttpDelete(BASE_URL + testUrl)).thenReturn(httpDelete);

        BitPayClient testedClass = this.getTestedClass();

        // when
        try {
            testedClass.delete(testUrl, parameters);
        } catch (Exception e) {
            // missing response
        }

        // then
        Mockito.verify(httpClient, Mockito.times(1)).execute(httpDelete);
        Mockito.verify(httpDelete, Mockito.times(1)).setURI(new URI("http://localhost/test?key=value"));
        Mockito.verify(httpDelete, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-signature"), ArgumentMatchers.anyString());
        Mockito.verify(httpDelete, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-identity"), ArgumentMatchers.anyString());
        Mockito.verify(httpDelete, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
        Mockito.verify(httpDelete, Mockito.times(1)).addHeader("x-accept-version", Config.BITPAY_API_VERSION);
        Mockito.verify(httpDelete, Mockito.times(1)).addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
        Mockito.verify(httpDelete, Mockito.times(1))
            .addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);
    }

    @Test
    public void it_should_prepare_post_request() throws IOException {
        // given
        final String testUrl = "/test";
        final String json = "{\"key\": \"value\"}";
        final HttpPost httpPost = Mockito.mock(HttpPost.class);

        Mockito.when(this.httpRequestFactory.createHttpPost(BASE_URL + testUrl)).thenReturn(httpPost);

        BitPayClient testedClass = this.getTestedClass();

        // when
        try {
            testedClass.post(testUrl, json, true);
        } catch (Exception e) {
            // missing response
        }

        // then
        Mockito.verify(httpClient, Mockito.times(1)).execute(httpPost);
        Mockito.verify(httpPost, Mockito.times(1)).setEntity(ArgumentMatchers.any(ByteArrayEntity.class));
        Mockito.verify(httpPost, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-signature"), ArgumentMatchers.anyString());
        Mockito.verify(httpPost, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-identity"), ArgumentMatchers.anyString());
        Mockito.verify(httpPost, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
        Mockito.verify(httpPost, Mockito.times(1)).addHeader("x-accept-version", Config.BITPAY_API_VERSION);
        Mockito.verify(httpPost, Mockito.times(1)).addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
        Mockito.verify(httpPost, Mockito.times(1))
            .addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);
    }

    @Test
    public void it_should_prepare_put_request() throws BitPayGenericException, BitPayApiException, IOException {
        // given
        final String testUrl = "/test";
        final String json = "{\"key\": \"value\"}";
        final HttpPut httpPut = Mockito.mock(HttpPut.class);

        Mockito.when(this.httpRequestFactory.createHttpPut(BASE_URL + testUrl)).thenReturn(httpPut);

        BitPayClient testedClass = this.getTestedClass();

        // when
        try {
            testedClass.update(testUrl, json);
        } catch (Exception e) {
            // missing response
        }

        // then
        Mockito.verify(httpClient, Mockito.times(1)).execute(httpPut);
        Mockito.verify(httpPut, Mockito.times(1)).setEntity(ArgumentMatchers.any(ByteArrayEntity.class));
        Mockito.verify(httpPut, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-signature"), ArgumentMatchers.anyString());
        Mockito.verify(httpPut, Mockito.times(1))
            .addHeader(ArgumentMatchers.eq("x-identity"), ArgumentMatchers.anyString());
        Mockito.verify(httpPut, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
        Mockito.verify(httpPut, Mockito.times(1)).addHeader("x-accept-version", Config.BITPAY_API_VERSION);
        Mockito.verify(httpPut, Mockito.times(1)).addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
        Mockito.verify(httpPut, Mockito.times(1))
            .addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);
    }



    private BitPayClient getTestedClass() {
        return new BitPayClient(this.httpClient, this.httpRequestFactory, BASE_URL, new ECKey());
    }
}
