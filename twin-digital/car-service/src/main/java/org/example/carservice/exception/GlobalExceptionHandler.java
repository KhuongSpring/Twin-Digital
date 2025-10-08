package org.example.carservice.exception;

import org.example.carservice.base.VsResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ServiceUnavailableException.class)
  public ResponseEntity<?> handleServiceUnavailableException(ServiceUnavailableException ex) {
    return VsResponseUtil.error(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<?> handleInternalServerException(InternalServerException ex) {
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, errors);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGlobalException(Exception ex) {
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, "Đã xảy ra lỗi: " + ex.getMessage());
  }
}
