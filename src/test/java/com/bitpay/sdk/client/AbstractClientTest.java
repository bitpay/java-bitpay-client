/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.util.AccessTokens;
import com.bitpay.sdk.util.GuidGenerator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bitcoinj.core.ECKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AbstractClientTest {

    public static final String EXAMPLE_UUID = "ee26b5e0-9185-493e-bc12-e846d5fcf07c";
    protected static final String MERCHANT_TOKEN = "someMerchantToken";
    protected static final String PAYOUT_TOKEN = "somePayoutToken";

    protected HttpServer httpServer;

    @Mock
    protected GuidGenerator uuidGenerator;

    private int port = 8000;

    public AbstractClientTest() {
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        this.port = ThreadLocalRandom.current().nextInt(8000, 9000);
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        this.httpServer.start();
    }

    @AfterEach
    public void afterEach() {
        this.httpServer.stop(0);
    }

    protected String getPreparedJsonDataFromFile(String fileName) {
        final String pathname = "src/test/java/com/bitpay/sdk/client/json/" + fileName;
        File file = new File(pathname);

        String data = null;
        try {
            data = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            return "";
        }

        return data;
    }

    protected void addServerJsonResponse(String path, String method, String expectedRequestJson, String jsonResponse) {
        String query = null;
        try {
            final URI uri = new URI(path);
            path = uri.getPath();
            query = uri.getQuery();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String queryFromPath = query;
        httpServer.createContext(path, new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {

                InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder(512);
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                br.close();
                isr.close();

                if (Objects.isNull(expectedRequestJson) || buf.toString().equals(expectedRequestJson)) {
                    final String exchangeQuery = exchange.getRequestURI().getQuery();
                    if (Objects.nonNull(exchangeQuery) && !exchangeQuery.equals(queryFromPath)) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                        exchange.close();
                    }

                    byte[] response = jsonResponse.getBytes();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
                    exchange.getResponseBody().write(response);
                    exchange.close();
                    return;
                }

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                exchange.close();
            }
        });
    }

    protected BitPayClient getBitPayClient() {
        return new BitPayClient(
            HttpClientBuilder.create().build(),
            new HttpRequestFactory(),
            "http://localhost:" + this.port + "/",
            new ECKey()
        );
    }

    protected AccessTokens getAccessTokens() {
        AccessTokens accessTokens = new AccessTokens();
        accessTokens.addMerchant(MERCHANT_TOKEN);
        accessTokens.addPayout(PAYOUT_TOKEN);

        return accessTokens;
    }
}
