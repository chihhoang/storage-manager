package com.storage.app.exception;

import java.io.IOException;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SystemExceptionHandler {

  @ExceptionHandler(SystemException.class)
  public void handleSystemException(HttpServletResponse res, SystemException ex)
      throws IOException {
    res.sendError(ex.getHttpStatus().value(), ex.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
    res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
  }

  @ExceptionHandler(AuthenticationException.class)
  public void handleAuthenticationException(HttpServletResponse res) throws IOException {
    res.sendError(HttpStatus.UNAUTHORIZED.value(), "Error authenticate user");
  }

  @ExceptionHandler(Exception.class)
  public void handleException(HttpServletResponse res) throws IOException {
    res.sendError(HttpStatus.BAD_REQUEST.value(), "Bad request");
  }
}
