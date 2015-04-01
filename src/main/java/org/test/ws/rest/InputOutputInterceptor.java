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
public class InputOutputInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest,
                                        byte[] bytes,
                                        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        System.out.println(String.format(
                "time_in: %s " +
                        "| thread: %s " +
                        "| payload-req-len: %s " +
                        "| endpoint: %s ",
                new Date().getTime(), Thread.currentThread().getId(),
                bytes.length, httpRequest.getURI().toString()));

        IOException globalError = null;
        ClientHttpResponse res = null;
        byte[] data = new byte[0];
        try {
            res = clientHttpRequestExecution.execute(httpRequest, bytes);
            InputStream body = res.getBody();
            data = new byte[body.available()];
            body.read(data);
            body.reset();
            body.close();
        } catch (IOException e) {
            globalError = e;
        }

        //TODO error
        System.out.println(String.format(
                "time_out: %s " +
                        "| thread: %s " +
                        "| http-status: %s " +
                        "| error: %s " +
                        "| payload-res-len: %s ",
                new Date().getTime(), Thread.currentThread().getId(),
                res != null ? res.getRawStatusCode() : "", globalError != null ? globalError.toString() : "", data.length));

        if (globalError != null) {
            throw globalError;
        }

        return res;
    }
}
