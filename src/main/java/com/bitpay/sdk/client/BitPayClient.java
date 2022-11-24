/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.Config;
import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.util.BitPayLogger;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.KeyUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
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

public class BitPayClient {

    private final HttpClient httpClient;
    private final HttpRequestFactory httpRequestFactory;
    private final String baseUrl;
    private final ECKey ecKey;
    private static BitPayLogger logger = new BitPayLogger(BitPayLogger.OFF);

    public BitPayClient(HttpClient httpClient, HttpRequestFactory httpRequestFactory, String baseUrl, ECKey ecKey) {
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
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse get(String uri, List<BasicNameValuePair> parameters) throws BitPayException {
        return get(uri, parameters, true);
    }

    /**
     * Send GET request.
     *
     * @param uri               the uri
     * @param parameters        the parameters
     * @param signatureRequired the signature required
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse get(String uri, List<BasicNameValuePair> parameters, boolean signatureRequired) throws BitPayException {
        try {
            String fullURL = this.baseUrl + uri;
            HttpGet httpGet = this.httpRequestFactory.createHttpGet(fullURL);

            if (parameters != null) {

                fullURL += "?" + URLEncodedUtils.format(parameters, "UTF-8");
                httpGet.setURI(new URI(fullURL));
            }
            if (signatureRequired) {
                httpGet.addHeader("x-signature", KeyUtils.sign(this.ecKey, fullURL));
                httpGet.addHeader("x-identity", KeyUtils.bytesToHex(this.ecKey.getPubKey()));
            }
            httpGet.addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
            httpGet.addHeader("x-accept-version", Config.BITPAY_API_VERSION);
            httpGet.addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
            httpGet.addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);


            logger.info(httpGet.toString());
            return httpClient.execute(httpGet);

        } catch (URISyntaxException e) {
            throw new BitPayException(null, "Error: GET failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException(null, "Error: GET failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "Error: GET failed\n" + e.getMessage());
        }
    }

    /**
     * Send GET request.
     *
     * @param uri the uri
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse get(String uri) throws BitPayException {
        return this.get(uri, null, false);
    }

    public HttpResponse delete(String uri, List<BasicNameValuePair> parameters) throws BitPayException {
        try {

            String fullURL = this.baseUrl + uri;
            HttpDelete httpDelete = this.httpRequestFactory.createHttpDelete(fullURL);

            if (parameters != null) {

                fullURL += "?" + URLEncodedUtils.format(parameters, "UTF-8");

                httpDelete.setURI(new URI(fullURL));

                httpDelete.addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
                httpDelete.addHeader("x-accept-version", Config.BITPAY_API_VERSION);
                httpDelete.addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
                httpDelete.addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);
                httpDelete.addHeader("x-signature", KeyUtils.sign(this.ecKey, fullURL));
                httpDelete.addHeader("x-identity", KeyUtils.bytesToHex(this.ecKey.getPubKey()));
            }

            logger.info(httpDelete.toString());
            return httpClient.execute(httpDelete);

        } catch (URISyntaxException e) {
            throw new BitPayException(null, "Error: DELETE failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException(null, "Error: DELETE failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "Error: DELETE failed\n" + e.getMessage());
        }
    }

    /**
     * Send POST request.
     *
     * @param uri  the uri
     * @param json the json
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse post(String uri, String json) throws BitPayException {
        return this.post(uri, json, false);
    }

    /**
     * Send POST request.
     *
     * @param uri               the uri
     * @param json              the json
     * @param signatureRequired the signature required
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse post(String uri, String json, boolean signatureRequired) throws BitPayException {
        try {
            HttpPost httpPost = this.httpRequestFactory.createHttpPost(this.baseUrl + uri);

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

            logger.info(httpPost.toString());
            return this.httpClient.execute(httpPost);

        } catch (UnsupportedEncodingException e) {
            throw new BitPayException(null, "Error: POST failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException(null, "Error: POST failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "Error: POST failed\n" + e.getMessage());
        }
    }

    /**
     * Send PUT request.
     *
     * @param uri  the uri
     * @param json the json
     * @return the http response
     * @throws BitPayException the bit pay exception
     */
    public HttpResponse update(String uri, String json) throws BitPayException {
        try {
            HttpPut put = this.httpRequestFactory.createHttpPut(this.baseUrl + uri);

            put.setEntity(new ByteArrayEntity(json.getBytes(StandardCharsets.UTF_8)));

            put.addHeader("x-signature", KeyUtils.sign(this.ecKey, this.baseUrl + uri + json));
            put.addHeader("x-identity", KeyUtils.bytesToHex(this.ecKey.getPubKey()));
            put.addHeader("x-accept-version", Config.BITPAY_API_VERSION);
            put.addHeader("X-BitPay-Plugin-Info", Config.BITPAY_PLUGIN_INFO);
            put.addHeader("Content-Type", "application/json");
            put.addHeader("x-bitpay-api-frame", Config.BITPAY_API_FRAME);
            put.addHeader("x-bitpay-api-frame-version", Config.BITPAY_API_FRAME_VERSION);

            logger.info(put.toString());
            return httpClient.execute(put);

        } catch (UnsupportedEncodingException e) {
            throw new BitPayException(null, "Error: PUT failed\n" + e.getMessage());
        } catch (ClientProtocolException e) {
            throw new BitPayException(null, "Error: PUT failed\n" + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null, "Error: PUT failed\n" + e.getMessage());
        }
    }

    /**
     * Convert HttpResponse for Json string.
     *
     * @param response the response
     * @return the string
     * @throws BitPayException the bit pay exception
     */
    public String responseToJsonString(HttpResponse response) throws BitPayException {
        if (response == null) {
            throw new BitPayException(null, "Error: HTTP response is null");
        }

        try {
            // Get the JSON string from the response.
            HttpEntity entity = response.getEntity();

            String jsonString;

            jsonString = EntityUtils.toString(entity, "UTF-8");
            logger.info("RESPONSE: " + jsonString);
            JsonMapper mapper = JsonMapperFactory.create();

            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            if (node != null) {
                if (node.toString().replace("\"", "").equals("error")) {
                    throw new BitPayException(rootNode.get("code").textValue(), rootNode.get("message").textValue());
                }
            }

            node = rootNode.get("error");

            if (node != null) {
                throw new BitPayException(null, "Error: " + node.asText());
            }

            node = rootNode.get("errors");

            if (node != null) {
                String message = "Multiple errors:";

                if (node.isArray()) {
                    for (final JsonNode errorNode : node) {
                        message += "\n" + errorNode.asText();
                    }

                    throw new BitPayException(null, message);
                }
            }

            node = rootNode.get("status");
            if (node != null) {
                if (node.toString().replace("\"", "").equals("error")) {
                    throw new BitPayException(rootNode.get("code").textValue(), rootNode.get("message").textValue());
                }
                if (node.toString().replace("\"", "").equals("success")) {
                    node = rootNode.get("data");

                    if (node.toString().equals("{}")) {
                        return rootNode.toString();
                    }
                }
            }

            node = rootNode.get("data");

            if (node != null) {
                jsonString = node.toString();
            }

            return jsonString;

        } catch (ParseException e) {
            throw new BitPayException(null, "failed to retrieve HTTP response body : " + e.getMessage());
        } catch (JsonMappingException e) {
            throw new BitPayException(null, "failed to parse json response to map : " + e.getMessage());
        } catch (BitPayException e) {
            throw new BitPayException(e.getStatusCode(), e.getReasonPhrase());
        } catch (Exception e) {
            throw new BitPayException(null, "failed to retrieve HTTP response body : " + e.getMessage());
        }
    }

    /**
     * Sets the logger level of reporting.
     *
     * @param loggerLevel int BitPayLogger constant (OFF, INFO, WARN, ERR, DEBUG)
     */
    public void setLoggerLevel(int loggerLevel) {
        logger = new BitPayLogger(loggerLevel);
    }
}
