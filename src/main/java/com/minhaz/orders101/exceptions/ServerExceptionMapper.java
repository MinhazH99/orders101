package com.minhaz.orders101.exceptions;

import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServerExceptionMapper implements ExceptionMapper<ServerErrorException> {
  @Override
  public Response toResponse(ServerErrorException exception) {
    String message = exception.getMessage();
    Response response = exception.getResponse();
    Response.Status status = response.getStatusInfo().toEnum();

    return Response.status(status).entity(status + ": " + message).type(MediaType.TEXT_PLAIN).build();
  }
}
