package fr.kata.bankAccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountStatementTest {

    @Test
    void can_register_operation() {
        // given
        AccountStatement accountStatement = new AccountStatement(() -> LocalDateTime.parse("2022-01-17T22:33:12"));
        // when
        accountStatement.registerOperation(AccountOperationType.DEPOSIT, BigDecimal.valueOf(20), BigDecimal.valueOf(30));
        // then
        Assertions.assertEquals(accountStatement.getOperationsAt(0).getType(), AccountOperationType.DEPOSIT);
        Assertions.assertEquals(accountStatement.getOperationsAt(0).getAmount(), BigDecimal.valueOf(20));
        Assertions.assertEquals(accountStatement.getOperationsAt(0).getBalance(), BigDecimal.valueOf(30));
        Assertions.assertEquals(accountStatement.getOperationsAt(0).getDate(), LocalDateTime.parse("2022-01-17T22:33:12"));
    }
}
