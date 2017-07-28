package me.test.first.spring.boot.jersey.resource;

import org.springframework.stereotype.*;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.util.*;

/**
 *
 */
@Component
@Provider
public class MyExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response
                .status(500)
                .type(MediaType.APPLICATION_JSON)
                .entity(
                        Arrays.asList(
                                exception.getMessage() + " @ MyExceptionMapper : " + new Date()
                        )
                )
                .build();
    }
}