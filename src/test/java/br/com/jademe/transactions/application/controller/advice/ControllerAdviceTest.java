package br.com.jademe.transactions.application.controller.advice;

import br.com.jademe.transactions.business.exception.ApplicationException;
import br.com.jademe.transactions.business.exception.InvalidDocumentNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class ControllerAdviceTest {

  @InjectMocks
  private ControllerAdvice controllerAdvice;

  @Test
  void handleApplicationException() {
    ApplicationException ex = new InvalidDocumentNumber();
    ResponseErrorMessage errorMessage = controllerAdvice.handleApplicationException(ex);
    assertEquals(errorMessage.getMessage(), ex.getMessage());
  }

}