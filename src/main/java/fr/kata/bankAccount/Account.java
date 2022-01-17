package fr.kata.bankAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

final public class Account {
    private final BigDecimal balance;
    private final List<AccountOperation> operations;
    private final Supplier<LocalDateTime> currentDateTime;

    public Account(BigDecimal initialBalance, Supplier<LocalDateTime> currentDateTime) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountInitializationException("Account cannot be initialized with negative balance or null");
        }
        this.balance = initialBalance;
        this.operations = new ArrayList<>();
        this.currentDateTime = currentDateTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<AccountOperation> getOperations() {
        return List.copyOf(operations);
    }

    public Account deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Amount cannot be negative or null when deposit");
        }
        BigDecimal newBalance = this.balance.add(amount);
        this.operations.add(new AccountOperation(AccountOperationType.DEPOSIT, amount, newBalance, currentDateTime.get()));
        return new Account(newBalance, this.currentDateTime);
    }

    public Account withdrawal(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Cannot withdrawal null amount");
        }
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Not enough savings on account to withdrawal");
        }
        return new Account(newBalance, currentDateTime);
    }
}
