/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.util.TokenContainer;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AuthorizationClientTest extends AbstractClientTest {

    public AuthorizationClientTest() throws IOException {
    }

    @Test
    public void it_should_authorize_client_by_facade() throws BitPayGenericException, BitPayApiException {
        // given
        Mockito.when(this.uuidGenerator.execute()).thenReturn(AbstractClientTest.EXAMPLE_UUID);
        this.addServerJsonResponse(
            "/tokens",
            "POST",
            getPreparedJsonDataFromFile("authorizeClientByFacadeRequest.json"),
            getPreparedJsonDataFromFile("authorizeClientByFacadeResponse.json")
        );

        final TokenContainer accessToken = new TokenContainer();
        AuthorizationClient authorizationClient = getAuthorizationClient(accessToken);

        String token = authorizationClient.authorizeClient(Facade.MERCHANT);

        Assertions.assertEquals("C4Lg7oW", token);
        Assertions.assertEquals(accessToken.getAccessToken(Facade.MERCHANT),
            "G7XM9fcM1gtCN7DUr8ZWtPGVFLTKiYWanHR4kvqsnjP3");
    }

    private AuthorizationClient getAuthorizationClient(TokenContainer accessToken) {
        return new AuthorizationClient(
            getBitPayClient(),
            this.uuidGenerator,
            accessToken,
            "someIdentity"
        );
    }
}
