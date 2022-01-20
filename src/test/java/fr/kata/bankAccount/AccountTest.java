package fr.kata.bankAccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTest {

    @Test
    void cannot_be_initialized_with_statement_null () {
        // when
        Executable callable = () -> new Account(null);
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void cannot_be_initiate_with_null_balance() {
        // when
        Executable callable = () -> new Account(null, new AccountStatement(LocalDateTime::now));
        // then
        Assertions.assertThrows(AccountInitializationException.class, callable);
    }

    @Test
    void cannot_be_initiate_with_negative_balance() {
        // when
        Executable callable = () -> new Account(BigDecimal.valueOf(-1), new AccountStatement(LocalDateTime::now));
        // then
        Assertions.assertThrows(AccountInitializationException.class, callable);
    }

    @Test
    void should_return_account_with_Zero_by_default() {
        // when
        AccountStatement accountStatement = new AccountStatement(LocalDateTime::now);
        Account account = new Account(accountStatement);
        // then
        Assertions.assertEquals(account.getBalance(), BigDecimal.ZERO);
    }

    @Test
    void should_make_deposit_on_account_for_valid_amount() {
        // given
        AccountStatement accountStatement = new AccountStatement(LocalDateTime::now);
        Account account = new Account(accountStatement);
        // when
        Account updatedAccount = account.deposit(BigDecimal.valueOf(12)).deposit(BigDecimal.valueOf(15));
        // then
        Assertions.assertEquals(updatedAccount.getBalance(), BigDecimal.valueOf(27));
    }

    @Test
    void should_raise_illegal_operation_exception_when_deposit_negative_amount() {
        // given
        AccountStatement accountStatement = new AccountStatement(LocalDateTime::now);
        Account account = new Account(accountStatement);
        // when
        Executable callable = () -> account.deposit(BigDecimal.valueOf(-1));
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_raise_illegal_operation_exception_when_deposit_null() {
        // given
        AccountStatement accountStatement = new AccountStatement(LocalDateTime::now);
        Account account = new Account(accountStatement);
        // when
        Executable callable = () -> account.deposit(null);
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_withdrawal_savings() {
        // given
        AccountStatement accountStatement = new AccountStatement(LocalDateTime::now);
        Account account = new Account(BigDecimal.valueOf(35), accountStatement);
        // when
        Account updatedAccount = account.withdrawal(BigDecimal.valueOf(12));
        // then
        Assertions.assertEquals(updatedAccount.getBalance(), BigDecimal.valueOf(23));
    }

    @Test
    void should_raise_illegal_operation_exception_when_withdrawal_null() {
        // given
        AccountStatement accountStatement = new AccountStatement(LocalDateTime::now);
        Account account = new Account(accountStatement);
        // when
        Executable callable = () -> account.withdrawal(null);
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_raise_illegal_operation_exception_when_withdrawal_negative_amount() {
        // given
        AccountStatement accountStatement = new AccountStatement(LocalDateTime::now);
        Account account = new Account(accountStatement);
        // when
        Executable callable = () -> account.withdrawal(BigDecimal.valueOf(-1));
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_raise_illegal_operation_exception_when_not_enough_savings() {
        // given
        AccountStatement accountStatement = new AccountStatement(LocalDateTime::now);
        Account account = new Account(accountStatement);
        // when
        Executable callable = () -> account.withdrawal(BigDecimal.valueOf(1));
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_register_deposit_operation_when_deposit_savings() {
        // given
        AccountStatement accountStatement = new AccountStatement(() -> LocalDateTime.parse("2022-01-17T22:33:12"));
        Account account = new Account(BigDecimal.valueOf(10), accountStatement);
        // when
        Account updatedAccount = account.deposit(BigDecimal.valueOf(20));
        // then
        Assertions.assertEquals(updatedAccount.getStatement().getOperationsAt(0).getType(), AccountOperationType.DEPOSIT);
        Assertions.assertEquals(updatedAccount.getStatement().getOperationsAt(0).getAmount(), BigDecimal.valueOf(20));
        Assertions.assertEquals(updatedAccount.getStatement().getOperationsAt(0).getBalance(), BigDecimal.valueOf(30));
        Assertions.assertEquals(updatedAccount.getStatement().getOperationsAt(0).getDate(), LocalDateTime.parse("2022-01-17T22:33:12"));
    }

    @Test
    void should_register_withdrawal_operation_when_withdrawal_savings() {
        // given
        AccountStatement accountStatement = new AccountStatement(() -> LocalDateTime.parse("2022-01-17T22:55:16"));
        Account account = new Account(BigDecimal.valueOf(16), accountStatement);
        // when
        Account updatedAccount = account.withdrawal(BigDecimal.valueOf(4));
        // then
        Assertions.assertEquals(updatedAccount.getStatement().getOperationsAt(0).getType(), AccountOperationType.WITHDRAWAL);
        Assertions.assertEquals(updatedAccount.getStatement().getOperationsAt(0).getAmount(), BigDecimal.valueOf(4));
        Assertions.assertEquals(updatedAccount.getStatement().getOperationsAt(0).getBalance(), BigDecimal.valueOf(12));
        Assertions.assertEquals(updatedAccount.getStatement().getOperationsAt(0).getDate(), LocalDateTime.parse("2022-01-17T22:55:16"));
    }
}
