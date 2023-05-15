/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

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
     * @throws BitPayException the bit pay exception
     */
    private CurrencyClient(BitPayClient client) throws BitPayException {
        if (Objects.isNull(client)) {
            throw new BitPayException(null, "failed init Currency Client");
        }
        this.client = client;
    }

    /**
     * Factory method for Currency Client.
     *
     * @param bitPayClient BitPay Client
     * @return CurrencyClient
     * @throws BitPayException BitPayException
     */
    public static CurrencyClient getInstance(BitPayClient bitPayClient) throws BitPayException {
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
     * @throws BitPayException the bit pay exception
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getInfo(String currencyCode) throws BitPayException {
        if (Objects.isNull(currencyCode)) {
            throw new BitPayException(null, "missing required parameter");
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
     *
     * @throws BitPayException BitPayException class
     */
    @SuppressWarnings("unchecked")
    private void loadCurrencies() throws BitPayException {
        try {
            HttpEntity newEntity = this.client.get("currencies").getEntity();

            String jsonString;

            jsonString = EntityUtils.toString(newEntity, "UTF-8");

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
