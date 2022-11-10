/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Token;
import com.bitpay.sdk.util.AccessTokenCache;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.UuidGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;

public class AuthorizationClient {

    private final BitPayClient bitPayClient;
    private final UuidGenerator uuidGenerator;
    private final AccessTokenCache tokenCache;
    private final String identity;

    public AuthorizationClient(
        BitPayClient bitPayClient,
        UuidGenerator uuidGenerator,
        AccessTokenCache tokenCache,
        String identity
    ) {
        this.bitPayClient = bitPayClient;
        this.uuidGenerator = uuidGenerator;
        this.tokenCache = tokenCache;
        this.identity = identity;
    }

    /**
     * Authorize (pair) this client with the server using the specified pairing code.
     *
     * @param pairingCode A code obtained from the server; typically from bitpay.com/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public void authorizeClient(String pairingCode) throws BitPayException {
        Token token = new Token();
        token.setId(this.identity);
        token.setGuid(this.uuidGenerator.execute());
        token.setPairingCode(pairingCode);

        JsonMapper mapper = JsonMapperFactory.create();

        String json;

        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to serialize Token object : " + e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("tokens", json);

        List<Token> tokens;

        try {
            tokens = Arrays.asList(mapper.readValue(this.bitPayClient.responseToJsonString(response), Token[].class));
        } catch (JsonProcessingException e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        }

        for (Token t : tokens) {
            this.tokenCache.put(t.getFacade(), t.getValue());
        }
    }

    /**
     * Request a pairing code from the BitPay server.
     *
     * @param facade Defines the level of API access being requested
     * @return A pairing code for claim at https://bitpay.com/dashboard/merchant/api-tokens.
     * @throws BitPayException BitPayException class
     */
    public String requestClientAuthorization(String facade) throws BitPayException {
        Token token = new Token();
        token.setId(this.identity);
        token.setGuid(this.uuidGenerator.execute());
        token.setFacade(facade);
        token.setCount(1);

        JsonMapper mapper = JsonMapperFactory.create();

        String json;

        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            throw new BitPayException(null, "failed to serialize Token object : " + e.getMessage());
        }

        HttpResponse response = this.bitPayClient.post("tokens", json);

        List<Token> tokens;

        try {
            tokens = Arrays.asList(mapper.readValue(this.bitPayClient.responseToJsonString(response), Token[].class));

            // Expecting a single token resource.
            if (tokens.size() != 1) {
                throw new BitPayException(null, "failed to get token resource; expected 1 token, got " + tokens.size());
            }

        } catch (JsonProcessingException e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        } catch (Exception e) {
            throw new BitPayException(null,
                "failed to deserialize BitPay server response (Tokens) : " + e.getMessage());
        }

        this.tokenCache.put(tokens.get(0).getFacade(), tokens.get(0).getValue());

        return tokens.get(0).getPairingCode();
    }
}
