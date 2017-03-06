package me.test.first.spring.boot.cxf.api

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.ws.WebFault

@WebFault(name = "NoSuchCustomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class NoSuchCustomerException extends RuntimeException {

    /**
     * We only define the fault details here. Additionally each fault has a message
     * that should not be defined separately
     */
    String customerName;
}