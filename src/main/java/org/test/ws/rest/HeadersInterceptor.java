package org.test.ws.rest;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author luis mora
 */
public class HeadersInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest,
                                        byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        httpRequest.getHeaders().add("Authorization", "a");

        ClientHttpResponse res = clientHttpRequestExecution.execute(httpRequest, bytes);
        System.out.println("res.getHeaders().toString() = " + res.getHeaders().toString());
        return res;
    }
}
