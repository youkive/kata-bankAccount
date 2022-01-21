package fr.kata.bankAccount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class AccountStatementPrinterTest {

    private PrintStream printer;
    private Path tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("", "");
        printer = new PrintStream(tempFile.toFile());
    }

    @AfterEach
    void tearDown() {
        if(printer != null) {
            printer.close();
        }
    }

    @Test
    void cannot_be_initialized_with_null_printer() {
        // when
        Executable callable = () -> new AccountStatementPrinter(null);
        // then
        Assertions.assertThrows(RuntimeException.class, callable);
    }

    @Test
    void should_print_statement_of_account() throws IOException {
        // given
        AccountStatement accountStatement = new AccountStatement(() -> LocalDateTime.parse("2022-01-18T10:21:02"))
                .registerOperation(AccountOperationType.DEPOSIT, BigDecimal.valueOf(54.26), BigDecimal.valueOf(55.6))
                .registerOperation(AccountOperationType.WITHDRAWAL, BigDecimal.valueOf(10), BigDecimal.valueOf(44.26));
        AccountStatementPrinter accountPrinter = new AccountStatementPrinter(printer);
        // when
        accountPrinter.print(accountStatement);
        // then
        List<String> statementLines = Files.readAllLines(tempFile);
        Assertions.assertEquals(statementLines.get(0), "Date ## Operation Type ## Amount ## Balance after operation");
        Assertions.assertEquals(statementLines.get(1), "2022-01-18 10:21:02 ## DEPOSIT ## 54.26 ## 55.60");
        Assertions.assertEquals(statementLines.get(2), "2022-01-18 10:21:02 ## WITHDRAWAL ## 10.00 ## 44.26");
    }
}
