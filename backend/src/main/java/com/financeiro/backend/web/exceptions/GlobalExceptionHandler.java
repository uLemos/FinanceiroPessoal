package com.financeiro.backend.web.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request){
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> 
      errors.put(error.getField(), error.getDefaultMessage()));

    ApiError apiError = new ApiError(
      HttpStatus.BAD_REQUEST,
      "Erro de validação nos dados enviados",
      request.getDescription(false).replace("uri=",""),
      errors
    );

    return ResponseEntity.badRequest().body(apiError);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex, WebRequest request){

    ApiError apiError = new ApiError(
      HttpStatus.NOT_FOUND,
      ex.getMessage(),
      request.getDescription(false).replace("uri=", "")
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, WebRequest request){

    logger.error("Erro interno no servidor", ex);

    ApiError apiError = new ApiError(
      HttpStatus.INTERNAL_SERVER_ERROR,
      "Erro interno no servidor",
      request.getDescription(false).replace("uri=", "")
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
      ApiError apiError = new ApiError(
          HttpStatus.BAD_REQUEST,
          ex.getMessage(),
          request.getDescription(false).replace("uri=", "")
      );
      return ResponseEntity.badRequest().body(apiError);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex, WebRequest request) {
      ApiError apiError = new ApiError(
          HttpStatus.CONFLICT,
          ex.getMessage(),
          request.getDescription(false).replace("uri=", "")
      );
      return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
  }

  public static class ApiError{

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> validationErrors;
    
    public ApiError(HttpStatus status, String message, String path) {
      this.timestamp = LocalDateTime.now();
      this.status = status.value();
      this.error = status.getReasonPhrase();
      this.message = message;
      this.path = path;
    }

    public ApiError(HttpStatus status, String message, String path, Map<String, String> validationErrors) {
        this(status, message, path);
        this.validationErrors = validationErrors;
    }

    public LocalDateTime getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public String getError() {
      return error;
    }

    public void setError(String error) {
      this.error = error;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    public Map<String, String> getValidationErrors() {
      return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
      this.validationErrors = validationErrors;
    }
  }
}
