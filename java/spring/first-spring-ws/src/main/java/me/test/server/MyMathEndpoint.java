package me.test.server;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;

import me.test.ws.model.AddRequest;
import me.test.ws.model.AddResponse;
import me.test.ws.model.MinusRequest;
import me.test.ws.model.MinusResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.addressing.server.annotation.Action;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * <ul>
 * 说明：<code>@PayloadRoot</code> 和 <code>@SoapAction</code> 都是用来配置 Endpoint
 * Mapping的，区别在于：
 * <li><code>@SoapAction</code> : 当消息是未加密时，且未指定 soap action时，可以
 * <code>&lt;SOAP-ENV:Body/&gt;</code> 中根元素的命名空间和元素名称进行映射</li>
 * <li><code>@PayloadRoot</code> : 当消息加密时，只能通过soap action 这个信息进行映射了。</li>
 * </ul>
 *
 * @param req
 * @return
 */
@Endpoint
public class MyMathEndpoint {
    public static final Logger logger = LoggerFactory
            .getLogger(MyMathEndpoint.class);

    public static final String NAMESPACE_URI = "http://www.test.me/MyMath/";

    // Using PayloadRootAnnotationMethodEndpointMapping
    // @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
    // public @ResponsePayload
    // Element add0(@RequestPayload Source req) {
    // logger.debug("add0 : req == {" + req + "}");
    // return genSampleAddResopnse(1, 2);
    // }

    // Using SoapActionAnnotationMethodEndpointMapping
    // 注意： <sws:dynamic-wsdl /> 生成的动态wsdl中 <soap:operation/> 的
    // <code>soapAction</code> 会为空
//    @SoapAction(value = NAMESPACE_URI + "add")
//    public @ResponsePayload
//    Element add1(@RequestPayload Source req) {
//        logger.debug("add1 : req == {" + req + "}");
//        return genSampleAddResopnse(1, 2);
//    }
    /*
    // Using PayloadRootAnnotationMethodEndpointMapping
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addRequest")
    public @ResponsePayload
    AddResponse add2(@RequestPayload AddRequest req) {
        int x = req.getX();
        int y = req.getY();
        int out = x + y;
        logger.debug("add2 : req == {x:" + x + ", y:" + y + "}, resp = {out : "
                + out + "}");
        AddResponse resp = new AddResponse();
        resp.setOut(out);
        return resp;
    }
*/
    // Using AnnotationActionEndpointMapping
    @Action(value = NAMESPACE_URI + "add")
    public @ResponsePayload
    AddResponse add3(@RequestPayload AddRequest req) {
        int x = req.getX();
        int y = req.getY();
        int out = x + y;
        logger.debug("add3 : req == {x:" + x + ", y:" + y + "}, resp = {out : "
                + out + "}");
        AddResponse resp = new AddResponse();
        resp.setOut(out);
        return resp;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "minusRequest")
    public @ResponsePayload
    MinusResponse minus(@RequestPayload MinusRequest req) {

        int x = req.getX();
        int y = req.getY();
        int out = x - y;
        logger.debug("minus : req == {x:" + x + ", y:" + y
                + "}, resp = {out : " + out + "}");
        MinusResponse resp = new MinusResponse();
        resp.setOut(out);
        return resp;

    }

    public Element genSampleAddResopnse(int x, int y) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Document doc = builder.newDocument();

        Element out = doc.createElement("t:out");
        out.appendChild(doc.createTextNode("333"));

        Element addResponse = doc.createElementNS(NAMESPACE_URI,
                "t:addResponse");
        addResponse.appendChild(out);
        return addResponse;
    }
}
