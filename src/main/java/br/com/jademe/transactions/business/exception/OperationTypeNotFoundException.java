package br.com.jademe.transactions.business.exception;

public class OperationTypeNotFoundException extends ApplicationException {

  private static final String errorMsg = "Tipo de operação inválido!";

  public OperationTypeNotFoundException() {
    super(errorMsg);
  }

}
