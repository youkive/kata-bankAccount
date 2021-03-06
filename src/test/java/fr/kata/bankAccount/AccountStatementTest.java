package fr.kata.bankAccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountStatementTest {

    @Test
    void cannot_initialize_statement_with_null_date_time_supplier() {
        // when
        Executable callable = () -> new AccountStatement(null);
        // then
        Assertions.assertThrows(NullPointerException.class, callable);
    }

    @Test
    void can_register_operation() {
        // given
        AccountStatement accountStatement = new AccountStatement(() -> LocalDateTime.parse("2022-01-17T22:33:12"));
        // when
        AccountStatement accountStatementToTest = accountStatement.registerOperation(AccountOperationType.DEPOSIT, BigDecimal.valueOf(20), BigDecimal.valueOf(30));
        // then
        Assertions.assertEquals(accountStatementToTest.getOperationsAt(0).getType(), AccountOperationType.DEPOSIT);
        Assertions.assertEquals(accountStatementToTest.getOperationsAt(0).getAmount(), BigDecimal.valueOf(20));
        Assertions.assertEquals(accountStatementToTest.getOperationsAt(0).getBalance(), BigDecimal.valueOf(30));
        Assertions.assertEquals(accountStatementToTest.getOperationsAt(0).getDate(), LocalDateTime.parse("2022-01-17T22:33:12"));
    }
}
