package fr.kata.bankAccount;

public class IllegalOperationArgumentException extends RuntimeException {
    public IllegalOperationArgumentException(String message) {
        super(message);
    }
}
