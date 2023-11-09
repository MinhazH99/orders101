package com.minhaz.orders101.exceptions;

import com.minhaz.orders101.endpoints.response.Errors;
import com.minhaz.orders101.endpoints.response.Detail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.ArrayList;

@Provider
public class ValidationConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response.status(Response.Status.BAD_REQUEST).entity(populateErrors(exception)).type("application/json")
        .build();
  }

  private Errors populateErrors(ConstraintViolationException exception) {
    Errors errors = new Errors(new ArrayList<>());
    for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
      errors.getConstraintViolations().add(new Detail(cv.getPropertyPath().toString(), cv.getMessage()));
    }
    return errors;
  }

}
