/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Currency client.
 */
public class CurrencyClient implements ResourceClient {

    private static CurrencyClient instance;
    private final BitPayClient client;
    private List<Map<String, String>> currenciesInfo;

    /**
     * Instantiates a new Currency client.
     *
     * @param client the client
     * @throws BitPayGenericException BitPayGenericException class
     */
    private CurrencyClient(BitPayClient client) throws BitPayGenericException {
        if (Objects.isNull(client)) {
            BitPayExceptionProvider.throwGenericExceptionWithMessage("Failed init Currency Client");
        }
        this.client = client;
    }

    /**
     * Factory method for Currency Client.
     *
     * @param bitPayClient BitPay Client
     * @return CurrencyClient
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static CurrencyClient getInstance(BitPayClient bitPayClient) throws BitPayGenericException {
        if (Objects.isNull(instance)) {
            instance = new CurrencyClient(bitPayClient);
        }

        return instance;
    }

    /**
     * Gets info for specific currency.
     *
     * @param currencyCode String Currency code for which the info will be retrieved.
     * @return Map |null
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getInfo(String currencyCode) throws BitPayGenericException {
        if (Objects.isNull(currencyCode)) {
            BitPayExceptionProvider.throwMissingParameterException();
        }

        if (Objects.isNull(this.currenciesInfo)) {
            this.loadCurrencies();
        }

        for (Object currency : this.currenciesInfo) {
            Map<String, Object> currencyInfo = JsonMapperFactory.create().convertValue(currency, Map.class);
            if (currencyInfo.get("code").toString().equals(currencyCode)) {
                return currencyInfo;
            }
        }

        return null;
    }

    /**
     * Load currencies info.
     */
    private void loadCurrencies() {
        try {
            String jsonString = this.client.get("currencies");

            JsonMapper mapper = JsonMapperFactory.create();

            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("data");

            if (node != null) {
                jsonString = node.toString();
            }

            this.currenciesInfo = new ArrayList(Arrays.asList(
                JsonMapperFactory.create().readValue(jsonString, Map[].class))
            );

        } catch (Exception e) {
            // No action required
        }
    }
}
