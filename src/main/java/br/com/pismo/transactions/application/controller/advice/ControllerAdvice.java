package br.com.pismo.transactions.application.controller.advice;

import br.com.pismo.transactions.business.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(ApplicationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseErrorMessage handleApplicationException(ApplicationException ex) {
    ResponseErrorMessage responseMsg = new ResponseErrorMessage(ex.getMessage());
    return responseMsg;
  }
}
