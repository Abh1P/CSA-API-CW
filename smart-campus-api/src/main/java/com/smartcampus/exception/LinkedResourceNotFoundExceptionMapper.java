package com.smartcampus.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 422, "Unprocessable Entity");
        return Response.status(422) // 422 Unprocessable Entity
                .entity(errorMessage)
                .build();
    }
}
