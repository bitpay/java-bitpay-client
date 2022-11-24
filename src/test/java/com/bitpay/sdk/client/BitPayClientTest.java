/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.Env;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.bitcoinj.core.ECKey;
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
    public void it_should_test_get_request() throws IOException, URISyntaxException {
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
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Env.BitpayPluginInfo);
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("x-accept-version", Env.BitpayApiVersion);
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("x-bitpay-api-frame", Env.BitpayApiFrame);
        Mockito.verify(httpGet, Mockito.times(1)).addHeader("x-bitpay-api-frame-version", Env.BitpayApiFrameVersion);
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
        Mockito.verify(httpDelete, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Env.BitpayPluginInfo);
        Mockito.verify(httpDelete, Mockito.times(1)).addHeader("x-accept-version", Env.BitpayApiVersion);
        Mockito.verify(httpDelete, Mockito.times(1)).addHeader("x-bitpay-api-frame", Env.BitpayApiFrame);
        Mockito.verify(httpDelete, Mockito.times(1)).addHeader("x-bitpay-api-frame-version", Env.BitpayApiFrameVersion);
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
        Mockito.verify(httpPost, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Env.BitpayPluginInfo);
        Mockito.verify(httpPost, Mockito.times(1)).addHeader("x-accept-version", Env.BitpayApiVersion);
        Mockito.verify(httpPost, Mockito.times(1)).addHeader("x-bitpay-api-frame", Env.BitpayApiFrame);
        Mockito.verify(httpPost, Mockito.times(1)).addHeader("x-bitpay-api-frame-version", Env.BitpayApiFrameVersion);
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
        Mockito.verify(httpPut, Mockito.times(1)).addHeader("X-BitPay-Plugin-Info", Env.BitpayPluginInfo);
        Mockito.verify(httpPut, Mockito.times(1)).addHeader("x-accept-version", Env.BitpayApiVersion);
        Mockito.verify(httpPut, Mockito.times(1)).addHeader("x-bitpay-api-frame", Env.BitpayApiFrame);
        Mockito.verify(httpPut, Mockito.times(1)).addHeader("x-bitpay-api-frame-version", Env.BitpayApiFrameVersion);
    }

    private BitPayClient getTestedClass() {
        return new BitPayClient(this.httpClient, new ECKey(), this.httpRequestFactory, BASE_URL);
    }
}
