package com.minhaz.orders101.exception;

import com.minhaz.orders101.model.ResponseModel;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.*;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
  @Override
  public Response toResponse(IllegalArgumentException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(ResponseModel.builder().errors(populateError(exception)).build()).build();
  }

  private List<Map<String, String>> populateError(IllegalArgumentException exception) {
    return List.of(Map.of("Error", exception.getMessage()));
  }
}
