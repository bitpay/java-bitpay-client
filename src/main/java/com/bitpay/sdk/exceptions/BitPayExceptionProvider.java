/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

package com.bitpay.sdk.exceptions;

import com.bitpay.sdk.logger.LoggerProvider;
import java.util.Objects;

public class BitPayExceptionProvider {

    public static final String GENERIC_API_UNMAPPED_ERROR_CODE = "000000";

    /**
     * Throw Generic (Non-Api) Exception.
     *
     * @param message error message
     * @throws BitPayGenericException BitPayGenericException class
     */
    public static void throwGenericExceptionWithMessage(String message) throws BitPayGenericException {
        logErrorMessage(message);

        throw new BitPayGenericException(message);
    }

    /**
     * Throw API Exception.
     *
     * @param message error message
     * @throws BitPayApiException BitPayApiException class
     */
    public static void throwApiExceptionWithMessage(String message) throws BitPayApiException {
        logErrorMessage(message);

        throw new BitPayApiException(message, GENERIC_API_UNMAPPED_ERROR_CODE);
    }

    /**
     * Throw API Exception with specific message and code.
     *
     * @param message error message
     * @param code BitPay error code
     * @throws BitPayApiException BitPayApiException
     */
    public static void throwApiExceptionWithMessage(String message, String code) throws BitPayApiException {
        logErrorMessage(message);

        throw new BitPayApiException(message, code);
    }

    public static void throwValidationException(String message) throws BitPayValidationException {
        logErrorMessage(message);

        throw new BitPayValidationException(message);
    }

    public static void throwMissingParameterException() throws BitPayValidationException {
        final String message = "Missing required parameter";

        logErrorMessage(message);

        throw new BitPayValidationException(message);
    }

    public static void throwDeserializeResourceException(String resource, String exceptionMessage)
        throws BitPayGenericException {
        String message = String.format(
            "Failed to deserialize BitPay server response ( %s ) : " + exceptionMessage, resource);
        BitPayExceptionProvider.throwGenericExceptionWithMessage(message);
    }

    public static void throwDeserializeException(String exceptionMessage)
        throws BitPayGenericException {
        String message = "Failed to deserialize BitPay server response : " + exceptionMessage;
        BitPayExceptionProvider.throwGenericExceptionWithMessage(message);
    }

    public static void throwEncodeException(String exceptionMessage)
        throws BitPayGenericException {
        String message = "Failed to encode params : " + exceptionMessage;
        BitPayExceptionProvider.throwGenericExceptionWithMessage(message);
    }

    public static void throwSerializeResourceException(String resource, String exceptionMessage)
        throws BitPayGenericException {
        String message = String.format("Failed to serialize ( %s ) : " + exceptionMessage, resource);
        BitPayExceptionProvider.throwGenericExceptionWithMessage(message);
    }

    public static void throwSerializeParamsException(String exceptionMessage) throws BitPayGenericException {
        String message = "Failed to serialize params : " + exceptionMessage;
        BitPayExceptionProvider.throwGenericExceptionWithMessage(message);
    }

    public static void throwInvalidCurrencyException(String currencyCode) throws BitPayValidationException {
        String message = String.format("Currency code %s must be a type of Model.Currency", currencyCode);
        BitPayExceptionProvider.throwValidationException(message);
    }

    private static void logErrorMessage(String message) {
        if (Objects.nonNull(message)) {
            LoggerProvider.getLogger().logError(message);
        }
    }
}
