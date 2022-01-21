package fr.kata.bankAccount;

import java.math.BigDecimal;

public class Account {
    private final BigDecimal balance;
    private final AccountStatement accountStatement;

    Account(AccountStatement accountStatement) {
        this(BigDecimal.ZERO, accountStatement);
    }

    Account(BigDecimal newBalance, AccountStatement accountStatement) {
        if (accountStatement == null) {
            throw new IllegalAccountOperationArgumentException("Statement cannot be null");
        }
        if (newBalance == null || newBalance.signum() < 0) {
            throw new AccountInitializationException("Account cannot be initialized with negative balance or null");
        }
        this.accountStatement = accountStatement;
        this.balance = newBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountStatement getStatement() {
        return this.accountStatement;
    }

    public Account deposit(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalAccountOperationArgumentException("Amount cannot be negative or null when deposit");
        }
        BigDecimal newBalance = this.balance.add(amount);
        return new Account(newBalance, this.accountStatement.registerOperation(AccountOperationType.DEPOSIT, amount, newBalance));
    }

    public Account withdrawal(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalAccountOperationArgumentException("Cannot withdrawal negative or null amount");
        }
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.signum() < 0) {
            throw new IllegalAccountOperationArgumentException("Not enough savings on account to withdrawal");
        }
        return new Account(newBalance, this.accountStatement.registerOperation(AccountOperationType.WITHDRAWAL, amount, newBalance));
    }

}
