package com.minhaz.orders101.exceptions;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {


  @Override
  public Response toResponse(NotFoundException exception) {
    return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type("application/json").build();
  }

}
