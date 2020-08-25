package br.com.pismo.transactions.business.exception;

public class InvalidDocumentNumber extends ApplicationException {

  private static final String errorMsg = "Número de documento não informado ou inválido!";

  public InvalidDocumentNumber() {
    super(errorMsg);
  }
}
