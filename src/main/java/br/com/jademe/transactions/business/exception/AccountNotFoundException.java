package br.com.jademe.transactions.business.exception;

public class AccountNotFoundException extends ApplicationException {

  private static final String errorMsg = "Conta não encontrada!";

  public AccountNotFoundException() {
    super(errorMsg);
  }
}
