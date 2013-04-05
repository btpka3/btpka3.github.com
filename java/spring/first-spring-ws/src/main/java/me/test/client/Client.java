package me.test.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import me.test.ws.model.AddRequest;
import me.test.ws.model.AddResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.addressing.client.ActionCallback;

public class Client extends WebServiceGatewaySupport {

    public static final String NAMESPACE_URI = "http://www.test.me/MyMath/";

    private Resource request;

    private URI action;

    public void setRequest(Resource request) {
        this.request = request;
    }

    public void setAction(URI action) {
        this.action = action;
    }

    public void add() throws IOException {

        AddRequest addReq = new AddRequest();
        addReq.setX(1);
        addReq.setY(2);
//        AddResponse addResp = (AddResponse) getWebServiceTemplate()
//                .marshalSendAndReceive(addReq);
//        AddResponse addResp = (AddResponse) getWebServiceTemplate()
//                .marshalSendAndReceive(addReq,
//                        new SoapActionCallback(NAMESPACE_URI + "add"));
        AddResponse addResp = null;
        try {
            addResp = (AddResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(addReq,
                            new ActionCallback(NAMESPACE_URI + "add"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        logger.debug("RESPONSE = " + addResp.getOut());
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "spring-ws-client.xml", Client.class);
        Client client = (Client) applicationContext.getBean("client");
        client.add();
    }

}
