package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResponseParserTest {

    @Test
    public void it_should_test_convert_success_response() throws BitPayApiException {
        // given
        final String responseContent = "{\"status\":\"success\",\"data\":{},\"message\":null}";

        // when
        String result = ResponseParser.getJsonDataFromJsonResponse(responseContent);

        // then
        Assertions.assertEquals(responseContent, result);
    }

    @Test
    public void it_should_return_data_value_from_json_response() throws BitPayApiException {
        // given
        final String responseContent = "{\"status\":\"success\",\"data\":{\"my\":\"data\"},\"message\":null}";

        // when
        String result = ResponseParser.getJsonDataFromJsonResponse(responseContent);

        // then
        Assertions.assertEquals("{\"my\":\"data\"}", result);
    }

    @Test
    public void it_should_throws_bitpay_api_exception_for_missing_response() {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                // given
                

                // when
                ResponseParser.getJsonDataFromJsonResponse(null);
            }
        );
        Assertions.assertEquals(
            "HTTP response is null",
            exception.getMessage()
        );
    }

    @Test
    public void it_should_throws_bitpay_api_exception_for_response_with_error() {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                // given
                final String responseContent = "{\"code\":\"500\",\"status\":\"error\",\"data\":{},\"message\":\"Error message text\"}";

                // when
                String result = ResponseParser.getJsonDataFromJsonResponse(responseContent);

                // then
                Assertions.assertEquals(responseContent, result);
            }
        );
        Assertions.assertEquals("Error message text", exception.getMessage());
    }

    @Test
    public void it_should_throws_bitpay_api_exception_for_response_with_errors() {
        BitPayApiException exception = Assertions.assertThrows(
            BitPayApiException.class,
            () -> {
                // given
                final String responseContent = "{\"errors\":[{\"error\":\"Missing required parameter.\",\"param\":\"price\"},{\"error\":\"Missing required parameter.\",\"param\":\"currency\"}]}";

                // when
                String result = ResponseParser.getJsonDataFromJsonResponse(responseContent);

                // then
                Assertions.assertEquals(responseContent, result);
            }
        );
        Assertions.assertEquals("Missing required parameter price. Missing required parameter currency.", exception.getMessage());
    }
}
