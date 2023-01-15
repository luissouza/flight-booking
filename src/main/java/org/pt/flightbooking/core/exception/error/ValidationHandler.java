package org.pt.flightbooking.core.exception.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

  private final ErrorBuilder errorBuilder;

  public ValidationHandler(
    final ErrorBuilder errorBuilder
  ) {
    this.errorBuilder = errorBuilder;
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
      final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

    return new ResponseEntity<>(errorBuilder.createError(
      ex.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage(),
      status),
      HttpStatus.BAD_REQUEST);
  }
}
