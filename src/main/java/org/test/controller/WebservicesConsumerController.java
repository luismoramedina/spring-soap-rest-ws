package org.test.controller;

import net.webservicex.Translate;
import net.webservicex.TranslateResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author luismoramedina
 */
@RestController
public class WebservicesConsumerController {

    @Resource(name = "webServiceTemplate")
    private WebServiceTemplate webServiceTemplate;

    @RequestMapping(value = {"/ws", "/"})
    public String handleRequest() {

        String translatedText = callWebService();

        return "hello! " + translatedText;
    }

    public String callWebService() {
        System.out.println("Recibida peticion");

        Translate requestPayload = new Translate();
        requestPayload.setLanguageMode("EnglishTOSpanish");
        requestPayload.setText("Hello");
        String soapAction = "http://www.webservicex.net/Translate";
        TranslateResponse response = (TranslateResponse) webServiceTemplate.marshalSendAndReceive(requestPayload, new SoapActionCallback(soapAction));

        String translateResult = "Error en la llamada!!";
        if (response != null) {
            translateResult = response.getTranslateResult();
        }

        translateResult = translateResult + " @ " + new Date();

        return translateResult;
    }
}
