package com.bitpay.sdk.client;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpResponseTest {

    private static final int CODE = 200;
    private static final String BODY = "anyBody";
    private static final String LOCALE = "en_US";
    private static final String HTTP_VERSION = "HTTP/1.1";

    @Test
    public void it_should_returns_code() {
        Assertions.assertEquals(CODE, this.getTestedClass().getCode());
    }

    @Test
    public void it_should_returns_body() {
        Assertions.assertSame(BODY, this.getTestedClass().getBody());
    }

    @Test
    public void it_should_returns_headers() {
        Assertions.assertSame("application/json", this.getTestedClass().getHeaders().get("Content-Type"));
    }

    @Test
    public void it_should_returns_locale() {
        Assertions.assertSame(LOCALE, this.getTestedClass().getLocale());
    }

    @Test
    public void it_should_returns_httpVersion() {
        Assertions.assertSame(HTTP_VERSION, this.getTestedClass().getHttpVersion());
    }

    private HttpResponse getTestedClass() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return new HttpResponse(CODE, BODY, headers, LOCALE, HTTP_VERSION);
    }
}
