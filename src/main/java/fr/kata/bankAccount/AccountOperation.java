package fr.kata.bankAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class AccountOperation {
    private final AccountOperationType type;
    private final BigDecimal amount;
    private final BigDecimal balance;
    private final LocalDateTime date;

    public AccountOperation(AccountOperationType type, BigDecimal amount, BigDecimal balance, LocalDateTime date) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.date = date;
    }

    public AccountOperationType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
