package br.com.jademe.transactions.business.exception;

public class TransactionAmountInvalidException extends ApplicationException {

  private static final String errorMsg = "Valor da transação inválido!";

  public TransactionAmountInvalidException() {
    super(errorMsg);
  }
}
