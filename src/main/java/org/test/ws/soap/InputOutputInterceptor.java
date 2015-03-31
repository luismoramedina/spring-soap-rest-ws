package org.test.ws.soap;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpUrlConnection;
import org.springframework.xml.transform.TransformerHelper;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Date;

/**
 * @author luis mora
 */
public class InputOutputInterceptor implements ClientInterceptor {
    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        HttpURLConnection connection = ((HttpUrlConnection) TransportContextHolder.getTransportContext().getConnection()).getConnection();

        System.out.println(String.format(
                "time_in: %s " +
                        "| thread: %s " +
                        "| payload-req-len: %s " +
                        "| endpoint: %s ",
                new Date().getTime(), Thread.currentThread().getId(),
                getPayloadSize(messageContext.getRequest()), connection.getURL().toString()));
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        return false;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception e) throws WebServiceClientException {
        Integer responseCode = null;
        HttpURLConnection connection = ((HttpUrlConnection) TransportContextHolder.getTransportContext().getConnection()).getConnection();
        try {
            responseCode = connection.getResponseCode();
        } catch (IOException e1) {
        }

        System.out.println(String.format(
                "time_out: %s " +
                        "| thread: %s " +
                        "| http-status: %s " +
                        "| error: %s " +
                        "| payload-res-len: %s ",
                new Date().getTime(), Thread.currentThread().getId(),
                responseCode, e != null ? e.toString() : "", getPayloadSize(messageContext.getResponse())));

    }

    protected int getPayloadSize(WebServiceMessage message) {
        Source source = message.getPayloadSource();
        TransformerHelper transformerHelper = new TransformerHelper();
        Transformer transformer = null;
        int length;
        try {
            transformer = transformerHelper.createTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");

            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            length = writer.toString().length();
        } catch (Exception e) {
            return 0;
        }

        return length;
    }
}
