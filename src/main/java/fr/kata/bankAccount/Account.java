package fr.kata.bankAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class Account {
    private final BigDecimal balance;
    private final Supplier<LocalDateTime> currentDateTime;
    private final AccountStatement accountStatement;

    public Account(AccountStatement accountStatement, Supplier<LocalDateTime> currentDateTime) {
        this(BigDecimal.ZERO, accountStatement, currentDateTime);
    }


    protected Account(BigDecimal newBalance, AccountStatement accountStatement, Supplier<LocalDateTime> currentDateTime) {
        this.accountStatement = accountStatement;
        if (newBalance == null || newBalance.signum() < 0) {
            throw new AccountInitializationException("Account cannot be initialized with negative balance or null");
        }
        this.balance = newBalance;
        this.currentDateTime = currentDateTime;
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
        AccountOperation accountOperation = new AccountOperation(AccountOperationType.DEPOSIT, amount, newBalance, currentDateTime.get());
        return new Account(newBalance, this.accountStatement.registerOperation(accountOperation), this.currentDateTime);
    }

    public Account withdrawal(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalAccountOperationArgumentException("Cannot withdrawal negative or null amount");
        }
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.signum() < 0) {
            throw new IllegalAccountOperationArgumentException("Not enough savings on account to withdrawal");
        }
        AccountOperation accountOperation = new AccountOperation(AccountOperationType.WITHDRAWAL, amount, newBalance, this.currentDateTime.get());
        return new Account(newBalance, this.accountStatement.registerOperation(accountOperation), this.currentDateTime);
    }

}
