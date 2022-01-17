package fr.kata.bankAccount;

public class IllegalAccountOperationArgumentException extends RuntimeException {
    public IllegalAccountOperationArgumentException(String message) {
        super(message);
    }
}
