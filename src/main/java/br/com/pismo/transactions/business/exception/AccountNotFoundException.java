package br.com.pismo.transactions.business.exception;

public class AccountNotFoundException extends ApplicationException {

    private static final String errorMsg = "Número de documento inválido!";

    public AccountNotFoundException() {
        super("Conta não encontrada!");
    }
}
