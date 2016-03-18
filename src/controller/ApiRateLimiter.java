package controller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

/**
 * Purpose is to proxy api requests in order to prevent response failures due to rate limiting in the api
 * this is only for POST requests, BitPay does not (at the time of this writing rate limit GET invoice
 */
public class ApiRateLimiter {

    private static int minimumTimeBetweenRequests = 0; //in milliseconds
    private static long lastSent = 0;

    public ApiRateLimiter(int minimumTimeBetweenRequests) {
        this.minimumTimeBetweenRequests = minimumTimeBetweenRequests;
    }

    public ApiRateLimiter() {
        this.minimumTimeBetweenRequests = 0;
    }

    public HttpResponse send(HttpPost post, HttpClient client) throws IOException {
        long timeNow = System.currentTimeMillis();
        while(timeNow < (lastSent + minimumTimeBetweenRequests)) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
            timeNow = System.currentTimeMillis();
        }
        lastSent = System.currentTimeMillis();
        return client.execute(post);
    }
}
