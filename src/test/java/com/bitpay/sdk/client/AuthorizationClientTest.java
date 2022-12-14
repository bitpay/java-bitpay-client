/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.util.AccessTokens;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AuthorizationClientTest extends AbstractClientTest {

    public AuthorizationClientTest() throws IOException {
    }

    @Test
    public void it_should_authorize_client_by_facade() throws IOException {
        // given
        Mockito.when(this.uuidGenerator.execute()).thenReturn(AbstractClientTest.EXAMPLE_UUID);
        this.addServerJsonResponse(
            "/tokens",
            "POST",
            getPreparedJsonDataFromFile("authorizeClientByFacadeRequest.json"),
            getPreparedJsonDataFromFile("authorizeClientByFacadeResponse.json")
        );

        final AccessTokens accessToken = new AccessTokens();
        AuthorizationClient authorizationClient = getAuthorizationClient(accessToken);

        try {
            String token = authorizationClient.authorizeClient(Facade.MERCHANT);

            Assertions.assertEquals("C4Lg7oW", token);
            Assertions.assertEquals(accessToken.getAccessToken(Facade.MERCHANT),
                "G7XM9fcM1gtCN7DUr8ZWtPGVFLTKiYWanHR4kvqsnjP3");
        } finally {
            httpServer.stop(0);
        }
    }

    @Test
    public void it_should_authorize_client_by_pairing_code() throws IOException {
        // given
        Mockito.when(this.uuidGenerator.execute()).thenReturn(AbstractClientTest.EXAMPLE_UUID);
        this.addServerJsonResponse(
            "/tokens",
            "POST",
            getPreparedJsonDataFromFile("authorizeClientByFacadeRequest.json"),
            getPreparedJsonDataFromFile("authorizeClientByFacadeResponse.json")
        );

        final AccessTokens accessToken = new AccessTokens();
        AuthorizationClient authorizationClient = getAuthorizationClient(accessToken);

        try {
            authorizationClient.authorizeClient("C4Lg7oW");

            Assertions.assertEquals(accessToken.getAccessToken(Facade.MERCHANT),
                "G7XM9fcM1gtCN7DUr8ZWtPGVFLTKiYWanHR4kvqsnjP3");
        } finally {
            httpServer.stop(0);
        }
    }

    private AuthorizationClient getAuthorizationClient(AccessTokens accessToken) {
        return new AuthorizationClient(
            getBitPayClient(),
            this.uuidGenerator,
            accessToken,
            "someIdentity"
        );
    }
}
