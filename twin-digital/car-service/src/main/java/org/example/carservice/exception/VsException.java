package org.example.carservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VsException extends RuntimeException {
  private String message;

  public VsException(String message) {
    this.message = message;
  }
}
