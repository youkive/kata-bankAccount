package fr.kata.bankAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountStatement {
    private final List<AccountOperation> operations;
    private final Supplier<LocalDateTime> currentDateTime;

    AccountStatement(Supplier<LocalDateTime> currentDateTime) {
        this(List.of(), currentDateTime);
    }

    private AccountStatement(List<AccountOperation> operations, Supplier<LocalDateTime> currentDateTime) {
        this.operations = List.copyOf(operations);
        this.currentDateTime = Objects.requireNonNull(currentDateTime);
    }

    public AccountOperation getOperationsAt(int operationIndex) {
        return this.operations.get(operationIndex);
    }

    public List<AccountOperation> getOperations() {
        return this.operations;
    }

    AccountStatement registerOperation(AccountOperationType type, BigDecimal amount, BigDecimal balance) {
        AccountOperation operationToRegister = new AccountOperation(type, amount, balance, currentDateTime.get());
        List<AccountOperation> operationsUpdated = Stream.concat(this.operations.stream(), Stream.of(operationToRegister)).collect(Collectors.toList());
        return new AccountStatement(operationsUpdated, currentDateTime);
    }
}
