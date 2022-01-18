package fr.kata.bankAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Account {
    private final BigDecimal balance;
    private final List<AccountOperation> operations;
    private final Supplier<LocalDateTime> currentDateTime;

    public Account(BigDecimal initialBalance, Supplier<LocalDateTime> currentDateTime) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountInitializationException("Account cannot be initialized with negative balance or null");
        }
        this.balance = initialBalance;
        this.operations = List.of();
        this.currentDateTime = currentDateTime;
    }

    private Account(BigDecimal newBalance, Supplier<LocalDateTime> currentDateTime, List<AccountOperation> operations) {
        this.balance = newBalance;
        this.operations = List.copyOf(operations);
        this.currentDateTime = currentDateTime;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<AccountOperation> getOperations() {
        return operations;
    }

    public Account deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Amount cannot be negative or null when deposit");
        }
        BigDecimal newBalance = this.balance.add(amount);
        AccountOperation accountOperation = new AccountOperation(AccountOperationType.DEPOSIT, amount, newBalance, currentDateTime.get());
        return updateAccountAndRegisterOperation(newBalance, accountOperation);
    }

    public Account withdrawal(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Cannot withdrawal null amount");
        }
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalAccountOperationArgumentException("Not enough savings on account to withdrawal");
        }
        AccountOperation accountOperation = new AccountOperation(AccountOperationType.WITHDRAWAL, amount, newBalance, this.currentDateTime.get());
        return updateAccountAndRegisterOperation(newBalance, accountOperation);
    }

    private Account updateAccountAndRegisterOperation(BigDecimal newBalance, AccountOperation accountOperation) {
        List<AccountOperation> operationsUpdated = Stream.concat(this.operations.stream(), Stream.of(accountOperation)).collect(Collectors.toList());
        return new Account(newBalance, this.currentDateTime, operationsUpdated);
    }
}
