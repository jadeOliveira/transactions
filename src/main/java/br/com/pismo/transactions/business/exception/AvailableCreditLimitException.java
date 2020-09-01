package br.com.pismo.transactions.business.exception;

public class AvailableCreditLimitException extends ApplicationException {

  private static final String errorMsg = "Transação não efetuada. Limite de crédito insuficiente.";

  public AvailableCreditLimitException() {
    super(errorMsg);
  }
}
