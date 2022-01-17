package fr.kata.bankAccount;

import java.math.BigDecimal;

public class Account {
    private final BigDecimal balance;

    public Account(BigDecimal balance) {
        if(balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountInitializationException("Account cannot be initialized with negative balance or null");
        }
        this.balance = balance;
    }

    public Account deposit(BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalOperationArgumentException("Amount cannot be negative or null when deposit");
        }
        return new Account(this.balance.add(amount));
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
