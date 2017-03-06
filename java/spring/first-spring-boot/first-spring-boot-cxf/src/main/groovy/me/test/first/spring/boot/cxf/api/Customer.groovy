package me.test.first.spring.boot.cxf.api

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    String name;
    String[] address;
    int numOrders;
    double revenue;
    BigDecimal test;
    Date birthDate;
    CustomerType type;
}