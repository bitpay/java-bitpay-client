/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import com.bitpay.sdk.util.JsonMapperFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;

public class ResponseParser {

    /**
     * Convert HttpResponse for Json string.
     *
     * @param jsonResponse the response
     * @return the string
     * @throws BitPayApiException the bit pay exception
     */
    public static String getJsonDataFromJsonResponse(final String jsonResponse) throws BitPayApiException {
        if (jsonResponse == null) {
            BitPayExceptionProvider.throwApiExceptionWithMessage("HTTP response is null");
        }

        String jsonString = null;

        try {
            final JsonMapper mapper = JsonMapperFactory.create();
            final JsonNode rootNode = mapper.readTree(jsonResponse);
            JsonNode node = rootNode.get("status");
            if (node != null) {
                final String status = node.toString().replace("\"", "");
                if ("error".equals(status)) {
                    BitPayExceptionProvider.throwApiExceptionWithMessage(
                        rootNode.get("message").textValue(),
                        getCode(rootNode)
                    );
                }

                node = rootNode.get("errors");
                if (node != null) {
                    String message = "";

                    if (node.isArray()) {
                        for (final JsonNode errorNode : node) {
                            message += "\n" + errorNode.asText();
                        }

                        BitPayExceptionProvider.throwApiExceptionWithMessage(
                            rootNode.get("message").textValue(),
                            getCode(rootNode)
                        );
                    }
                }


                if ("success".equals(status)) {
                    node = rootNode.get("data");

                    if ("{}".equals(node.toString())) {
                        return rootNode.toString();
                    }
                }
            }

            handleErrors(rootNode);

            node = rootNode.get("data");
            if (node != null) {
                return node.toString();
            }

            return jsonResponse;
        } catch (final IOException e) {
            BitPayExceptionProvider.throwApiExceptionWithMessage(e.getMessage());
        }

        return jsonString;
    }

    private static void handleErrors(JsonNode rootNode) throws BitPayApiException {

        handleError(rootNode);

        JsonNode errors = rootNode.get("errors");
        if (errors == null) {
            return;
        }

        StringBuilder finalMessage = new StringBuilder();

        for (JsonNode errorNode : errors) {
            String errorValue = errorNode.has("error") ? errorNode.get("error").textValue() : "";
            String paramValue = errorNode.has("param") ? errorNode.get("param").textValue() : "";

            if (errorValue != null && errorValue.endsWith(".")) {
                errorValue = errorValue.substring(0, errorValue.length() - 1);
            }

            String message;
            message = errorValue + " " + paramValue;
            message = message.trim();

            if (!message.endsWith(".")) {
                message += ".";
            }

            if (!finalMessage.toString().equals("")) {
                message = " " + message;
            }

            finalMessage.append(message);
        }

        BitPayExceptionProvider.throwApiExceptionWithMessage(finalMessage.toString(), getCode(rootNode));
    }

    private static void handleError(JsonNode rootNode) throws BitPayApiException {
        if (!rootNode.has("error")) {
            return;
        }

        BitPayExceptionProvider.throwApiExceptionWithMessage(
            rootNode.get("error").textValue(),
            getCode(rootNode)
        );
    }

    private static String getCode(JsonNode rootNode) {
        if (rootNode.has("code")) {
            return rootNode.get("code").textValue();
        }

        return null;
    }
}
