package net.webservicex;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.8
 * 2015-03-17T12:51:09.953+01:00
 * Generated source version: 2.7.8
 * 
 */
@WebService(targetNamespace = "http://www.webservicex.net", name = "TranslateServiceSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface TranslateServiceSoap {

    /**
     * Convert text from one lanaguage to another language .Supported languages are English to Chinese,English to French,English to German,English to Italian,English to Japanese,English to Korean,English to Portuguese,English to Spanish,Chinese to English,French to English,French to German,German to English,German to French,Italian to English,Japanese to English,Korean to English,Portuguese to English,Russian to English,Spanish to English.
     */
    @WebResult(name = "TranslateResult", targetNamespace = "http://www.webservicex.net")
    @RequestWrapper(localName = "Translate", targetNamespace = "http://www.webservicex.net", className = "net.webservicex.Translate")
    @WebMethod(operationName = "Translate", action = "http://www.webservicex.net/Translate")
    @ResponseWrapper(localName = "TranslateResponse", targetNamespace = "http://www.webservicex.net", className = "net.webservicex.TranslateResponse")
    public java.lang.String translate(
        @WebParam(name = "LanguageMode", targetNamespace = "http://www.webservicex.net")
        java.lang.String languageMode,
        @WebParam(name = "Text", targetNamespace = "http://www.webservicex.net")
        java.lang.String text
    );
}
