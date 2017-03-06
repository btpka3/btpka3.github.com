package me.test.first.spring.boot.cxf.api

import javax.jws.WebParam
import javax.jws.WebService

@WebService
public interface CustomerService {
    public Customer[] getCustomersByName(@WebParam(name = "name") String name) throws NoSuchCustomerException;
}