package org.test.controller;

import net.webservicex.Translate;
import net.webservicex.TranslateResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author luismoramedina
 */
@RestController
public class WebservicesConsumerController {

    @Resource(name = "webServiceTemplate")
    private WebServiceTemplate webServiceTemplate;

    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;

    @RequestMapping(value = {"/ws", "/"})
    public String handleWsRequest() {

        String translatedText = callWebService();

        return "hello! " + translatedText;
    }

    @RequestMapping(value = {"/rest"})
    public String handleRestRequest() {

        String data = callRestService();

        return "hello! " + data;
    }

    private String callRestService() {
        return new String(restTemplate.getForObject("http://localhost:8000/cars.json", byte[].class));
    }

    public String callWebService() {
        Translate requestPayload = new Translate();
        requestPayload.setLanguageMode("EnglishTOSpanish");
        requestPayload.setText("Hello");
        String soapAction = "http://www.webservicex.net/Translate";
        TranslateResponse response = (TranslateResponse) webServiceTemplate.marshalSendAndReceive(requestPayload, new SoapActionCallback(soapAction));

        String translateResult = "Error!!";
        if (response != null) {
            translateResult = response.getTranslateResult();
        }

        translateResult = translateResult + " @ " + new Date();

        return translateResult;
    }
}
