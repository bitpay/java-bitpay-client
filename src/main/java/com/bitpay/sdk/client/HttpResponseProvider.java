package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayExceptionProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

class HttpResponseProvider {

    public static HttpResponse fromApacheHttpResponse(org.apache.http.HttpResponse apacheHttpResponse)
        throws BitPayApiException {
        try {
            final HttpEntity entity = apacheHttpResponse.getEntity();
            String body = EntityUtils.toString(entity, "UTF-8");

            return new HttpResponse(
                apacheHttpResponse.getStatusLine().getStatusCode(),
                body,
                Arrays.stream(apacheHttpResponse.getAllHeaders())
                    .collect(Collectors.toMap(Header::getName, Header::getValue)),
                apacheHttpResponse.getLocale().toString(),
                apacheHttpResponse.getStatusLine().getProtocolVersion().toString()
            );

        } catch (IOException e) {
            BitPayExceptionProvider.throwApiExceptionWithMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
