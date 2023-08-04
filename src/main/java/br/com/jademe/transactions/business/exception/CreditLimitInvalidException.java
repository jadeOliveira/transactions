package br.com.jademe.transactions.business.exception;

public class CreditLimitInvalidException extends ApplicationException {

  private static final String errorMsg = "Limite de crédito inválido!";

  public CreditLimitInvalidException() {
    super(errorMsg);
  }
}
