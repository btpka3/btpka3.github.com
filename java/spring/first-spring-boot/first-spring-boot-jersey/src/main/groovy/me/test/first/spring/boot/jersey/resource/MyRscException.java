package me.test.first.spring.boot.jersey.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 *
 */
public class MyRscException extends WebApplicationException {


    public MyRscException(String message) {
        super(message, Response
                .status(501)
                .type(MediaType.APPLICATION_JSON)
                .entity(
                        Arrays.asList(
                                message + " @ MyRscException : " + new Date()
                        )
                )
                .build());
    }


}