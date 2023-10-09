/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.Config;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.logger.LoggerProvider;
import com.bitpay.sdk.util.KeyUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bitcoinj.core.ECKey;

/**
 * The type Bit pay client.
 */
public class BitPayClient {

    private final HttpClient httpClient;
    private final HttpRequestFactory httpRequestFactory;
    private final String baseUrl;
    private final ECKey ecKey;

    /**
     * Instantiates a new Bit pay client.
     *
     * @param httpClient         the http client
     * @param httpRequestFactory the http request factory
     * @param baseUrl            the base url
     * @param ecKey              the ECKey
     */
    public BitPayClient(
        final HttpClient httpClient,
        final HttpRequestFactory httpRequestFactory,
        final String baseUrl,
        final ECKey ecKey
    ) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
        this.httpRequestFactory = httpRequestFactory;
        this.ecKey = ecKey;
    }

    /**
     * Send GET request.
     *
     * @param uri        the uri
     * @param parameters the parameters
     * @return the http response
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     */
    public String get(
        final String uri,
        final List<BasicNameValuePair> parameters
    ) throws BitPayApiException, BitPayGenericException {
        return get(uri, parameters, true);
    }

    /**
     * Send GET request.
     *
     * @param uri               the uri
     * @param parameters        the parameters
     * @param signatureRequired the signature required
     * @return the json http response
     * @throws BitPayGenericException BitPayGenericException
     * @throws BitPayApiException BitPayApiException
     */
    public String get(
        final String uri,
        final List<BasicNameValuePair> parameters,
        final boolean signatureRequired
    ) throws BitPayApiException, BitPayGenericException {
        String jsonResponse = null;

        try {
            String fullUrl = this.baseUrl + uri;
            final HttpGet httpGet = this.httpRequestFactory.createHttpGet(fullUrl);

            if (parameters != null) {
                fullUrl += "?" + URLEncodedUtils.format(parameters, "UTF-8");
                httpGet.setURI(new URI(fullUrl));
            }

            if (signatureRequired) {
                httpGet.addHeader("x-signature", KeyUtils.sign(this.ecKey, fullUrl));
                httpGet.addHeader("x-identity", KeyUtils.bytesToHex(this.ecKey.getPubKey()));
            }

            httpGet.addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
            httpGet.addHeader("x-accept-version", Config.BITPAY_API_VERSION);
            httpGet.addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
            httpGet.addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);

            LoggerProvider.getLogger().logRequest(HttpGet.METHOD_NAME, fullUrl, null);

            HttpResponse response = this.httpClient.execute(httpGet);

            final HttpEntity entity = response.getEntity();
            String jsonString = EntityUtils.toString(entity, "UTF-8");

            LoggerProvider.getLogger().logResponse(HttpGet.METHOD_NAME, fullUrl, jsonString);

            jsonResponse = ResponseParser.getJsonDataFromJsonResponse(jsonString);

        } catch (IOException | URISyntaxException e) {
            BitPayExceptionProvider.throwApiExceptionWithMessage(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * Send GET request.
     *
     * @param uri the uri
     * @return json http response
     * @throws BitPayApiException BitPayApiException
     * @throws BitPayGenericException BitPayGenericException
     */
    public String get(final String uri) throws BitPayApiException, BitPayGenericException {
        return this.get(uri, null, false);
    }

    /**
     * Send DELETE request.
     *
     * @param uri        the uri
     * @param parameters the parameters
     * @return json http response
     * @throws BitPayApiException BitPayApiException
     * @throws BitPayGenericException BitPayGenericException
     */
    public String delete(
        final String uri,
        final List<BasicNameValuePair> parameters
    ) throws BitPayApiException, BitPayGenericException {
        String jsonResponse = null;

        try {
            String fullUrl = this.baseUrl + uri;
            final HttpDelete httpDelete = this.httpRequestFactory.createHttpDelete(fullUrl);

            if (parameters != null) {
                fullUrl += "?" + URLEncodedUtils.format(parameters, "UTF-8");
                httpDelete.setURI(new URI(fullUrl));
            }

            httpDelete.addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
            httpDelete.addHeader("x-accept-version", Config.BITPAY_API_VERSION);
            httpDelete.addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
            httpDelete.addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);
            httpDelete.addHeader("x-signature", KeyUtils.sign(this.ecKey, fullUrl));
            httpDelete.addHeader("x-identity", KeyUtils.bytesToHex(this.ecKey.getPubKey()));

            LoggerProvider.getLogger().logRequest(HttpDelete.METHOD_NAME, fullUrl, null);

            HttpResponse response = this.httpClient.execute(httpDelete);

            final HttpEntity entity = response.getEntity();
            String jsonString = EntityUtils.toString(entity, "UTF-8");

            LoggerProvider.getLogger().logResponse(HttpDelete.METHOD_NAME, fullUrl, jsonString);

            jsonResponse = ResponseParser.getJsonDataFromJsonResponse(jsonString);
        } catch (IOException | URISyntaxException e) {
            BitPayExceptionProvider.throwApiExceptionWithMessage(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * Send POST request.
     *
     * @param uri  the uri
     * @param json the json
     * @return the json http response
     * @throws BitPayApiException BitPayApiException
     * @throws BitPayGenericException BitPayGenericException
     */
    public String post(
        final String uri,
        final String json
    ) throws BitPayApiException, BitPayGenericException {
        return this.post(uri, json, false);
    }

    /**
     * Send POST request.
     *
     * @param uri               the uri
     * @param json              the json
     * @param signatureRequired the signature required
     * @return json response
     * @throws BitPayApiException BitPayApiException
     * @throws BitPayGenericException BitPayGenericException
     */
    public String post(
        final String uri,
        final String json,
        final boolean signatureRequired
    ) throws BitPayApiException, BitPayGenericException {
        String jsonResponse = null;

        try {
            final String endpoint = this.baseUrl + uri;
            final HttpPost httpPost = this.httpRequestFactory.createHttpPost(endpoint);

            httpPost.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));

            if (signatureRequired) {
                httpPost.addHeader("x-signature", KeyUtils.sign(this.ecKey, this.baseUrl + uri + json));
                httpPost.addHeader("x-identity", KeyUtils.bytesToHex(this.ecKey.getPubKey()));
            }

            httpPost.addHeader("x-accept-version", Config.BITPAY_API_VERSION);
            httpPost.addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
            httpPost.addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);
            httpPost.addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
            httpPost.addHeader("Content-Type", "application/json");

            LoggerProvider.getLogger().logRequest(HttpPost.METHOD_NAME, endpoint, httpPost.toString());

            HttpResponse response = this.httpClient.execute(httpPost);

            final HttpEntity entity = response.getEntity();
            String jsonString = EntityUtils.toString(entity, "UTF-8");

            LoggerProvider.getLogger().logResponse(HttpGet.METHOD_NAME, endpoint, jsonString);

            jsonResponse = ResponseParser.getJsonDataFromJsonResponse(jsonString);
        } catch (IOException e) {
            BitPayExceptionProvider.throwApiExceptionWithMessage(e.getMessage());
        }

        return jsonResponse;
    }

    /**
     * Send PUT request.
     *
     * @param uri  the uri
     * @param json the json
     * @return json response
     * @throws BitPayApiException BitPayApiException
     * @throws BitPayGenericException BitPayGenericException
     */
    public String update(
        final String uri,
        final String json
    ) throws BitPayApiException, BitPayGenericException {
        String jsonResponse = null;

        try {
            final String endpoint = this.baseUrl + uri;
            final HttpPut httpPut = this.httpRequestFactory.createHttpPut(endpoint);

            httpPut.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));

            httpPut.addHeader("x-signature", KeyUtils.sign(this.ecKey, this.baseUrl + uri + json));
            httpPut.addHeader("x-identity", KeyUtils.bytesToHex(this.ecKey.getPubKey()));
            httpPut.addHeader("x-accept-version", Config.BITPAY_API_VERSION);
            httpPut.addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
            httpPut.addHeader("Content-Type", "application/json");
            httpPut.addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
            httpPut.addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);

            LoggerProvider.getLogger().logRequest(HttpPut.METHOD_NAME, endpoint, httpPut.toString());

            HttpResponse response = this.httpClient.execute(httpPut);

            final HttpEntity entity = response.getEntity();
            String jsonString = EntityUtils.toString(entity, "UTF-8");

            LoggerProvider.getLogger().logResponse(HttpPut.METHOD_NAME, endpoint, jsonString);

            jsonResponse = ResponseParser.getJsonDataFromJsonResponse(jsonString);
        } catch (IOException e) {
            BitPayExceptionProvider.throwApiExceptionWithMessage(e.getMessage());
        }

        return jsonResponse;
    }
}
