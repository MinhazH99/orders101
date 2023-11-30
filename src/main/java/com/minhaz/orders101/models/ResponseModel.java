package com.minhaz.orders101.models;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseModel<T> {

  private T data;

  private List<Map<String, String>> errors;
}
