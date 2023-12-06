/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Token;
import com.bitpay.sdk.util.GuidGenerator;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.TokenContainer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The type Authorization client.
 */
public class AuthorizationClient {

    private final BitPayClient bitPayClient;
    private final GuidGenerator guidGenerator;
    private final TokenContainer accessToken;
    private final String identity;

    /**
     * Instantiates a new Authorization client.
     *
     * @param bitPayClient  the bit pay client
     * @param guidGenerator the guid generator
     * @param accessToken   the access token
     * @param identity      the identity
     */
    public AuthorizationClient(
        BitPayClient bitPayClient,
        GuidGenerator guidGenerator,
        TokenContainer accessToken,
        String identity
    ) {
        this.bitPayClient = bitPayClient;
        this.guidGenerator = guidGenerator;
        this.accessToken = accessToken;
        this.identity = identity;
    }

    /**
     * Authorize (pair) this client with the server using the specified pairing code.
     *
     * @param pairingCode A code obtained from the server; typically from bitpay.com/api-tokens.
     * @throws BitPayApiException BitPayApiException class
     * @throws BitPayGenericException BitPayGenericException class
     */
    public void authorizeClient(String pairingCode) throws BitPayApiException, BitPayGenericException {
        Token token = new Token();
        token.setId(this.identity);
        token.setGuid(this.guidGenerator.execute());
        token.setPairingCode(pairingCode);

        JsonMapper mapper = JsonMapperFactory.create();

        String json = null;

        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage(
                "Failed to serialize Token object : " + e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("tokens", json);

        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        List<Token> tokens = null;

        try {
            tokens = Arrays.asList(mapper.readValue(jsonResponse, Token[].class));
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Tokens", e.getMessage());
        }

        for (Token t : tokens) {
            this.accessToken.put(t.getFacade(), t.getValue());
        }
    }

    /**
     * Request a pairing code from the BitPay server.
     *
     * @param facade Defines the level of API access being requested
     * @return A pairing code for claim at https://bitpay.com/dashboard/merchant/api-tokens.
     * @throws BitPayGenericException BitPayGenericException class
     * @throws BitPayApiException BitPayApiException class
     */
    public String authorizeClient(Facade facade) throws BitPayApiException, BitPayGenericException {
        if (Objects.isNull(facade)) {
            BitPayExceptionProvider.throwValidationException("Missing required parameter");
        }

        Token token = new Token();
        token.setId(this.identity);
        token.setGuid(this.guidGenerator.execute());
        token.setFacade(facade.toString());
        token.setCount(1);

        JsonMapper mapper = JsonMapperFactory.create();

        String json = null;

        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwSerializeResourceException("Token", e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("tokens", json);

        String jsonResponse = ResponseParser.getJsonDataFromJsonResponse(response.getBody());

        List<Token> tokens = null;

        try {
            tokens = Arrays.asList(mapper.readValue(jsonResponse, Token[].class));

            // Expecting a single token resource.
            if (tokens.size() != 1) {
                BitPayExceptionProvider.throwDeserializeResourceException(
                    "Token",
                    "expected 1 token, got " + tokens.size()
                );
            }

        } catch (JsonProcessingException e) {
            BitPayExceptionProvider.throwDeserializeResourceException("Tokens", e.getMessage());
        }

        this.accessToken.put(tokens.get(0).getFacade(), tokens.get(0).getValue());

        return tokens.get(0).getPairingCode();
    }
}
