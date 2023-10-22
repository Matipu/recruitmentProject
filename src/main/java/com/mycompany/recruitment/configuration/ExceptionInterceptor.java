package com.mycompany.recruitment.configuration;

import com.mycompany.recruitment.user.exception.BusinessException;
import com.mycompany.recruitment.user.exception.TechnicalException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor {

  private static Map<String, Object> buildErrorBody(String ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex);
    return body;
  }

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<Object> businessExceptionHandler(BusinessException ex) {
    return new ResponseEntity<>(buildErrorBody(ex.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({TechnicalException.class})
  public ResponseEntity<Object> technicalExceptionHandler(TechnicalException ex) {
    return new ResponseEntity<>(buildErrorBody(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
