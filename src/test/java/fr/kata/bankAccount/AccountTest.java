package fr.kata.bankAccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;

public class AccountTest {

    @Test
    void cannot_be_initiate_with_null_balance() {
        // when
        Executable callable = () -> new Account(null);
        // then
        Assertions.assertThrows(AccountInitializationException.class, callable);
    }

    @Test
    void cannot_be_initiate_with_negative_balance() {
        // when
        Executable callable = () -> new Account(BigDecimal.valueOf(-1));
        // then
        Assertions.assertThrows(AccountInitializationException.class, callable);
    }

    @Test
    void should_make_deposit_on_account_for_valid_amount() {
        // given
        Account account = new Account(BigDecimal.valueOf(15));
        BigDecimal amount = BigDecimal.valueOf(12);
        // when
        Account updatedAccount = account.deposit(amount);
        // then
        Assertions.assertEquals(updatedAccount.getBalance(), BigDecimal.valueOf(27));
    }

    @Test
    void should_raise_illegal_operation_exception_when_deposit_negative_amount() {
        // given
        Account account = new Account(BigDecimal.ZERO);
        // when
        Executable callable = () -> account.deposit(BigDecimal.valueOf(-1));
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_raise_illegal_operation_exception_when_deposit_null() {
        // given
        Account account = new Account(BigDecimal.ZERO);
        // when
        Executable callable = () -> account.deposit(null);
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_withdrawal_savings () {
        // given
        Account account = new Account(BigDecimal.valueOf(35));
        // when
        Account updatedAccount = account.withdrawal(BigDecimal.valueOf(12));
        // then
        Assertions.assertEquals(updatedAccount.getBalance(), BigDecimal.valueOf(23));
    }

    @Test
    void should_raise_illegal_operation_exception_when_withdrawal_null() {
        // given
        Account account = new Account(BigDecimal.valueOf(14));
        // when
        Executable callable = () -> account.withdrawal(null);
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_raise_illegal_operation_exception_when_withdrawal_negative_amount() {
        // given
        Account account = new Account(BigDecimal.valueOf(15));
        // when
        Executable callable = () -> account.withdrawal(BigDecimal.valueOf(-1));
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }

    @Test
    void should_raise_illegal_operation_exception_when_not_enough_savings() {
        // given
        Account account = new Account(BigDecimal.ZERO);
        // when
        Executable callable = () -> account.withdrawal(BigDecimal.valueOf(1));
        // then
        Assertions.assertThrows(IllegalAccountOperationArgumentException.class, callable);
    }
}
