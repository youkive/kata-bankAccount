package fr.kata.bankAccount;

import java.math.BigDecimal;

public class Account {
    private final BigDecimal balance;

    public Account(BigDecimal initialBalance) {
        if(initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountInitializationException("Account cannot be initialized with negative balance or null");
        }
        this.balance = initialBalance;
    }

    public Account deposit(BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Amount cannot be negative or null when deposit");
        }
        return new Account(this.balance.add(amount));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account withdrawal(BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Cannot withdrawal null amount");
        }
        BigDecimal newBalance = this.balance.subtract(amount);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Not enough savings on account to withdrawal");
        }
        return new Account(newBalance);
    }
}
