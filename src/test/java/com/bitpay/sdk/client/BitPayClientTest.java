/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.Config;
import com.bitpay.sdk.exceptions.BitPayException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
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
    public void it_should_test_get_request_without_parameters() throws IOException, URISyntaxException {
        // given
        final String testUrl = "/test";
        final HttpGet httpGet = Mockito.mock(HttpGet.class);
        final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("key", "value"));

        Mockito.when(this.httpRequestFactory.createHttpGet(BASE_URL + testUrl)).thenReturn(httpGet);

        BitPayClient testedClass = this.getTestedClass();

        // when
        testedClass.get(testUrl, parameters);

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
    public void it_should_test_get_request_with_parameters() throws IOException, URISyntaxException {
        // given
        final String testUrl = "/test";
        final HttpGet httpGet = Mockito.mock(HttpGet.class);
        final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("key", "value"));

        Mockito.when(this.httpRequestFactory.createHttpGet(BASE_URL + testUrl)).thenReturn(httpGet);

        BitPayClient testedClass = this.getTestedClass();

        // when
        testedClass.get(testUrl, parameters, true);

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
    public void it_should_throws_bitpayexception_when_something_is_wrong_for_get_requests()
        throws IOException, URISyntaxException {
        BitPayException exception1 = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                final HttpGet httpGet = Mockito.mock(HttpGet.class);
                Mockito.when(this.httpRequestFactory.createHttpGet(ArgumentMatchers.anyString())).thenReturn(httpGet);
                BitPayClient testedClass = this.getTestedClass();
                testedClass.get("[/]", new ArrayList<BasicNameValuePair>(), true);
            }
        );

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: GET failed\n" +
                "Illegal character in hostname at index 16: http://localhost[/]?",
            exception1.getMessage()
        );

        BitPayException exception2 = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                final HttpGet httpGet = Mockito.mock(HttpGet.class);
                Mockito.when(this.httpRequestFactory.createHttpGet(ArgumentMatchers.anyString())).thenReturn(httpGet);
                Mockito.when(this.httpClient.execute(httpGet))
                    .thenThrow(new ClientProtocolException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.get("/test", new ArrayList<BasicNameValuePair>(), true);
            }
        );

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: GET failed\n" +
                "Exception message",
            exception2.getMessage()
        );


        BitPayException exception3 = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                final HttpGet httpGet = Mockito.mock(HttpGet.class);
                Mockito.when(this.httpRequestFactory.createHttpGet(ArgumentMatchers.anyString())).thenReturn(httpGet);
                Mockito.when(this.httpClient.execute(httpGet)).thenThrow(new IOException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.get("/test", new ArrayList<BasicNameValuePair>(), true);
            },
            "Error: GET failed\n" + "Exception message"
        );

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: GET failed\n" +
                "Exception message",
            exception3.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpayexception_when_something_is_wrong_for_delete_requests()
        throws IOException, URISyntaxException {
        BitPayException exception1 = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                final HttpDelete httpDelete = Mockito.mock(HttpDelete.class);
                Mockito.when(this.httpRequestFactory.createHttpDelete(ArgumentMatchers.anyString()))
                    .thenReturn(httpDelete);
                BitPayClient testedClass = this.getTestedClass();
                testedClass.delete("[/]", new ArrayList<BasicNameValuePair>());
            }
        );

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: DELETE failed\n" +
                "Illegal character in hostname at index 16: http://localhost[/]?",
            exception1.getMessage()
        );

        BitPayException exception2 = Assertions.assertThrows(
            BitPayException.class,
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
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: DELETE failed\n" +
                "Exception message",
            exception2.getMessage()
        );

        BitPayException exception3 = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                final HttpDelete httpDelete = Mockito.mock(HttpDelete.class);
                Mockito.when(this.httpRequestFactory.createHttpDelete(ArgumentMatchers.anyString()))
                    .thenReturn(httpDelete);
                Mockito.when(this.httpClient.execute(httpDelete)).thenThrow(new IOException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.delete("/test", new ArrayList<BasicNameValuePair>());
            }
        );

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: DELETE failed\n" +
                "Exception message",
            exception3.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpayexception_when_something_is_wrong_for_post_requests()
        throws IOException, URISyntaxException {
        BitPayException exception1 = Assertions.assertThrows(
            BitPayException.class,
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

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: POST failed\n" +
            "Exception message",
            exception1.getMessage()
        );


        BitPayException exception2 = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                final HttpPost httpPost = Mockito.mock(HttpPost.class);
                Mockito.when(this.httpRequestFactory.createHttpPost(ArgumentMatchers.anyString()))
                    .thenReturn(httpPost);
                Mockito.when(this.httpClient.execute(httpPost)).thenThrow(new IOException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.post("/test", "{\"key\":\"value\"}");
            }
        );

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: POST failed\n" +
                "Exception message",
            exception2.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpayexception_when_something_is_wrong_for_put_requests()
        throws IOException, URISyntaxException {
        BitPayException exception1 = Assertions.assertThrows(
            BitPayException.class,
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

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: PUT failed\n"
                + "Exception message",
            exception1.getMessage()
        );

        BitPayException exception2 = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                final HttpPut httpPut = Mockito.mock(HttpPut.class);
                Mockito.when(this.httpRequestFactory.createHttpPut(ArgumentMatchers.anyString()))
                    .thenReturn(httpPut);
                Mockito.when(this.httpClient.execute(httpPut)).thenThrow(new IOException("Exception message"));
                BitPayClient testedClass = this.getTestedClass();
                testedClass.update("[/]", "{\"key\":\"value\"}");
            }
        );

        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: PUT failed\n" +
                "Exception message",
            exception2.getMessage()
        );
    }

    @Test
    public void it_should_test_delete_request() throws IOException, URISyntaxException {
        // given
        final String testUrl = "/test";
        final HttpDelete httpDelete = Mockito.mock(HttpDelete.class);
        final List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
        parameters.add(new BasicNameValuePair("key", "value"));

        Mockito.when(this.httpRequestFactory.createHttpDelete(BASE_URL + testUrl)).thenReturn(httpDelete);

        BitPayClient testedClass = this.getTestedClass();

        // when
        testedClass.delete(testUrl, parameters);

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
    public void it_should_test_post_request() throws IOException, URISyntaxException {
        // given
        final String testUrl = "/test";
        final String json = "{\"key\": \"value\"}";
        final HttpPost httpPost = Mockito.mock(HttpPost.class);

        Mockito.when(this.httpRequestFactory.createHttpPost(BASE_URL + testUrl)).thenReturn(httpPost);

        BitPayClient testedClass = this.getTestedClass();

        // when
        testedClass.post(testUrl, json, true);

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
    public void it_should_test_put_request() throws IOException, URISyntaxException {
        // given
        final String testUrl = "/test";
        final String json = "{\"key\": \"value\"}";
        final HttpPut httpPut = Mockito.mock(HttpPut.class);

        Mockito.when(this.httpRequestFactory.createHttpPut(BASE_URL + testUrl)).thenReturn(httpPut);

        BitPayClient testedClass = this.getTestedClass();

        // when
        testedClass.update(testUrl, json);

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

    @Test
    public void it_should_test_convert_httpResponse_for_json() throws BitPayException {
        // given
        final String responseContent = "{\"status\":\"success\",\"data\":{},\"message\":null}";
        BitPayClient testedClass = this.getTestedClass();
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        HttpEntity httpEntity = new StringEntity(responseContent, "UTF-8");
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);

        // when
        String result = testedClass.responseToJsonString(httpResponse);

        // then
        Assertions.assertEquals(responseContent, result);
    }

    @Test
    public void it_should_return_data_value_from_httpResponse() throws BitPayException {
        // given
        final String responseContent = "{\"status\":\"success\",\"data\":{\"my\":\"data\"},\"message\":null}";
        BitPayClient testedClass = this.getTestedClass();
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        HttpEntity httpEntity = new StringEntity(responseContent, "UTF-8");
        Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);

        // when
        String result = testedClass.responseToJsonString(httpResponse);

        // then
        Assertions.assertEquals("{\"my\":\"data\"}", result);
    }

    @Test
    public void it_should_throws_bitpayexception_for_missing_response() {
        BitPayException exception = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                // given
                BitPayClient testedClass = this.getTestedClass();

                // when
                testedClass.responseToJsonString(null);
            }
        );
        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error: HTTP response is null",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpayexception_for_response_with_error() {
        BitPayException exception = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                // given
                final String responseContent = "{\"code\":\"500\",\"status\":\"error\",\"data\":{},\"message\":\"Error message text\"}";
                BitPayClient testedClass = this.getTestedClass();
                HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
                HttpEntity httpEntity = new StringEntity(responseContent, "UTF-8");
                Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);

                // when
                String result = testedClass.responseToJsonString(httpResponse);

                // then
                Assertions.assertEquals(responseContent, result);
            }
        );
        Assertions.assertEquals(
            "Status: 500 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Error message text",
            exception.getMessage()
        );

    }

    @Test
    public void it_should_throws_bitpayexception_for_response_with_errors() {
        BitPayException exception = Assertions.assertThrows(
            BitPayException.class,
            () -> {
                // given
                final String responseContent = "{\"code\":\"500\",\"status\":\"errors\",\"errors\":[\"Error1\", \"Error2\"],\"data\":{},\"message\":\"Error message text\"}";
                BitPayClient testedClass = this.getTestedClass();
                HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
                HttpEntity httpEntity = new StringEntity(responseContent, "UTF-8");
                Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);

                // when
                String result = testedClass.responseToJsonString(httpResponse);

                // then
                Assertions.assertEquals(responseContent, result);
            }
        );
        Assertions.assertEquals(
            "Status: 000000 -> Reason: BITPAY-GENERIC: Unexpected Bitpay exeption. -> Multiple errors:\n" +
                "Error1\n" + "Error2",
            exception.getMessage()
        );
    }

    private BitPayClient getTestedClass() {
        return new BitPayClient(this.httpClient, this.httpRequestFactory, BASE_URL, new ECKey());
    }
}
