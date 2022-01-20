package fr.kata.bankAccount;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountStatement {
    private final List<AccountOperation> operations;

    public AccountStatement() {
        operations = List.of();
    }

    private AccountStatement(List<AccountOperation> operations) {
        this.operations = List.copyOf(operations);
    }

    public AccountStatement registerOperation(AccountOperation accountOperation) {
        List<AccountOperation> operationsUpdated = Stream.concat(this.operations.stream(), Stream.of(accountOperation)).collect(Collectors.toList());
        return new AccountStatement(operationsUpdated);
    }

    public AccountOperation getOperationsAt(int operationIndex) {
        return this.operations.get(operationIndex);
    }

    public List<AccountOperation> getOperations() {
        return this.operations;
    }
}
