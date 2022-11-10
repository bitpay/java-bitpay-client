/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.exceptions.PayoutBatchCancellationException;
import com.bitpay.sdk.exceptions.PayoutBatchCreationException;
import com.bitpay.sdk.exceptions.PayoutBatchNotificationException;
import com.bitpay.sdk.exceptions.PayoutBatchQueryException;
import com.bitpay.sdk.exceptions.PayoutQueryException;
import com.bitpay.sdk.model.Facade;
import com.bitpay.sdk.model.Payout.PayoutBatch;
import com.bitpay.sdk.util.AccessTokenCache;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.bitpay.sdk.util.UuidGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

public class PayoutBatchClient {

    private final BitPayClient bitPayClient;
    private final AccessTokenCache accessTokenCache;
    private final UuidGenerator uuidGenerator;

    public PayoutBatchClient(
        BitPayClient bitPayClient,
        AccessTokenCache accessTokenCache,
        UuidGenerator uuidGenerator
    ) {
        this.bitPayClient = bitPayClient;
        this.accessTokenCache = accessTokenCache;
        this.uuidGenerator = uuidGenerator;
    }

    /**
     * Submit a BitPay Payout batch.
     *
     * @param batch PayoutBatch A PayoutBatch object with request parameters defined.
     * @return A BitPay generated PayoutBatch object.
     * @throws BitPayException              BitPayException class
     * @throws PayoutBatchCreationException PayoutBatchCreationException class
     */
    public PayoutBatch submitPayoutBatch(PayoutBatch batch) throws BitPayException, PayoutBatchCreationException {
        String token = this.accessTokenCache.getAccessToken(Facade.Payout);
        batch.setToken(token);
        batch.setGuid(this.uuidGenerator.execute());

        JsonMapper mapper = JsonMapperFactory.create();
        String json;

        try {
            json = mapper.writeValueAsString(batch);
        } catch (JsonProcessingException e) {
            throw new PayoutBatchCreationException(null, "failed to serialize PayoutBatch object : " + e.getMessage());
        }

        try {
            HttpResponse response = this.bitPayClient.post("payoutBatches", json, true);
            batch = mapper.readerForUpdating(batch).readValue(this.bitPayClient.responseToJsonString(response));
        } catch (Exception e) {
            throw new PayoutBatchCreationException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        this.accessTokenCache.cacheToken(batch.getId(), batch.getToken());

        return batch;
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException           BitPayException class
     * @throws PayoutBatchQueryException PayoutBatchQueryException class
     */
    public List<PayoutBatch> getPayoutBatches() throws BitPayException, PayoutBatchQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.Payout)));

        List<PayoutBatch> batches;

        try {
            HttpResponse response = this.bitPayClient.get("payouts", params);
            batches = Arrays
                .asList(new ObjectMapper()
                    .readValue(this.bitPayClient.responseToJsonString(response), PayoutBatch[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutBatchQueryException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutBatchQueryException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batches;
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @param status String The status to filter the Payout Batches.
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException      BitPayException class
     * @throws PayoutQueryException PayoutQueryException class
     */
    public List<PayoutBatch> getPayoutBatches(String status) throws BitPayException, PayoutQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.Payout)));
        params.add(new BasicNameValuePair("status", status));

        List<PayoutBatch> batches;

        try {
            HttpResponse response = this.bitPayClient.get("payouts", params);
            batches =
                Arrays.asList(new ObjectMapper()
                    .readValue(this.bitPayClient.responseToJsonString(response), PayoutBatch[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutQueryException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutQueryException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batches;
    }

    /**
     * Retrieve a collection of BitPay payout batches.
     *
     * @param startDate String The start date for the query.
     * @param endDate   String The end date for the query.
     * @param status    String The status to filter(optional).
     * @param limit     int Maximum results that the query will return (useful for
     *                  paging results).
     * @param offset    int Offset for paging.
     * @return A list of BitPay PayoutBatch objects.
     * @throws BitPayException           BitPayException class
     * @throws PayoutBatchQueryException PayoutBatchQueryException class
     */
    public List<PayoutBatch> getPayoutBatches(
        String startDate,
        String endDate,
        String status,
        Integer limit,
        Integer offset
    ) throws BitPayException, PayoutBatchQueryException {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.Payout)));
        if (startDate != null) {
            params.add(new BasicNameValuePair("startDate", startDate));
        }
        if (endDate != null) {
            params.add(new BasicNameValuePair("endDate", endDate));
        }
        if (status != null) {
            params.add(new BasicNameValuePair("status", status));
        }
        if (limit != null) {
            params.add(new BasicNameValuePair("limit", limit.toString()));
        }
        if (offset != null) {
            params.add(new BasicNameValuePair("offset", offset.toString()));
        }

        List<PayoutBatch> batches;

        try {
            HttpResponse response = this.bitPayClient.get("payoutBatches", params);
            batches = Arrays
                .asList(new ObjectMapper()
                    .readValue(this.bitPayClient.responseToJsonString(response), PayoutBatch[].class));
        } catch (JsonProcessingException e) {
            throw new PayoutBatchQueryException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutBatchQueryException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batches;
    }

    /**
     * Retrieve a BitPay payout batch by batch id using. The client must have been
     * previously authorized for the payout facade.
     *
     * @param payoutBatchId String The id of the batch to retrieve.
     * @return A BitPay PayoutBatch object.
     * @throws BitPayException           BitPayException class
     * @throws PayoutBatchQueryException PayoutBatchQueryException class
     */
    public PayoutBatch getPayoutBatch(String payoutBatchId) throws BitPayException, PayoutBatchQueryException {
        String token = this.accessTokenCache.getAccessToken(Facade.Payout);
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", token));

        PayoutBatch batch;

        try {
            HttpResponse response = this.bitPayClient.get("payoutBatches/" + payoutBatchId, params, true);
            batch = new ObjectMapper().readValue(this.bitPayClient.responseToJsonString(response), PayoutBatch.class);
        } catch (JsonProcessingException e) {
            throw new PayoutBatchQueryException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutBatchQueryException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return batch;
    }

    /**
     * Cancel a BitPay Payout batch.
     *
     * @param payoutBatchId String The id of the payout batch to cancel.
     * @return True if the refund was successfully canceled, false otherwise.
     * @throws BitPayException                  BitPayException class
     * @throws PayoutBatchCancellationException PayoutBatchCancellationException
     *                                          class
     */
    public Boolean cancelPayoutBatch(String payoutBatchId) throws BitPayException, PayoutBatchCancellationException {

        Boolean result;
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("token", this.accessTokenCache.getAccessToken(Facade.Payout)));

        JsonMapper mapper = JsonMapperFactory.create();

        try {
            HttpResponse response = this.bitPayClient.delete("payoutBatches/" + payoutBatchId, params);
            String jsonString = this.bitPayClient.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");

        } catch (JsonProcessingException e) {
            throw new PayoutBatchCancellationException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        } catch (Exception e) {
            throw new PayoutBatchCancellationException(null,
                "failed to deserialize BitPay server response (PayoutBatch) : " + e.getMessage());
        }

        return result;
    }

    /**
     * Request a payout batch notification
     *
     * @param payoutBatchId String The id of the payout batch to notify..
     * @return True if the notification was successfully sent, false otherwise.
     * @throws BitPayException                  BitPayException class
     * @throws PayoutBatchNotificationException PayoutBatchNotificationException class
     */
    public Boolean requestPayoutBatchNotification(String payoutBatchId)
        throws BitPayException, PayoutBatchNotificationException {
        final Map<String, String> params = new HashMap<>();
        params.put("token", this.accessTokenCache.getAccessToken(Facade.Payout));

        JsonMapper mapper = JsonMapperFactory.create();

        Boolean result;
        String json;

        try {
            json = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new PayoutBatchNotificationException(null,
                "failed to serialize payout batch object : " + e.getMessage());
        }

        try {
            HttpResponse response =
                this.bitPayClient.post("payoutBatches/" + payoutBatchId + "/notifications", json, true);
            String jsonString = this.bitPayClient.responseToJsonString(response);
            JsonNode rootNode = mapper.readTree(jsonString);
            JsonNode node = rootNode.get("status");
            result = node.toString().replace("\"", "").toLowerCase(Locale.ROOT).equals("success");
        } catch (BitPayException ex) {
            throw new PayoutBatchNotificationException(ex.getStatusCode(), ex.getReasonPhrase());
        } catch (Exception e) {
            throw new PayoutBatchNotificationException(null,
                "failed to deserialize BitPay server response (Payoutbatch) : " + e.getMessage());
        }

        return result;
    }
}
