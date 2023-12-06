package com.bitpay.sdk.functional.client;

import com.bitpay.sdk.client.HttpResponse;
import com.bitpay.sdk.client.HttpResponseProvider;
import com.bitpay.sdk.exceptions.BitPayApiException;
import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpResponseProviderTest {

    @Test
    public void it_should_correct_transfer_apache_response_to_bitpay_response() throws IOException, BitPayApiException {

        HttpClient apacheClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("https://jsonplaceholder.typicode.com/posts/1");
        org.apache.http.HttpResponse apacheResponse = apacheClient.execute(httpGet);

        HttpResponse httpResponse = HttpResponseProvider.fromApacheHttpResponse(apacheResponse);

        Assertions.assertEquals(200, httpResponse.getCode());
        Assertions.assertEquals("{\n" +
            "  \"userId\": 1,\n" +
            "  \"id\": 1,\n" +
            "  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
            "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
            "}", httpResponse.getBody());
    }
}
