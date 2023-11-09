package com.minhaz.orders101.endpoints.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Errors {

  List<Detail> constraintViolations;

}
