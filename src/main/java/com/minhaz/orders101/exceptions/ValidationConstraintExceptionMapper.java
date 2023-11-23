package com.minhaz.orders101.exceptions;

import com.minhaz.orders101.models.ResponseModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Provider
public class ValidationConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(ResponseModel.builder().errors(populateErrors(exception)).build()).build();
  }

  private List<Map> populateErrors(ConstraintViolationException exception) {
    List<Map> errors = new ArrayList<>();
    for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
      errors.add(new HashMap<>() {
        {
          put(cv.getPropertyPath().toString(), cv.getMessage());
        }
      });
    }
    return errors;
  }

}
