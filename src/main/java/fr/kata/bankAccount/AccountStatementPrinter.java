package fr.kata.bankAccount;

import java.io.PrintStream;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AccountStatementPrinter {
    private static final String SEPARATOR = " ## ";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final PrintStream printer;

    public AccountStatementPrinter(PrintStream printer) {
        this.printer = Objects.requireNonNull(printer);
    }

    public void print(AccountStatement accountStatement) {
        printer.println("Date ## Operation Type ## Amount ## Balance after operation");
        accountStatement.getOperations().forEach(operation -> {
            StringBuilder lineStatement = new StringBuilder(operation.getDate().format(DATE_TIME_FORMATTER));
            lineStatement.append(SEPARATOR).append(operation.getType());
            lineStatement.append(SEPARATOR).append(operation.getAmount().setScale(2, RoundingMode.DOWN));
            lineStatement.append(SEPARATOR).append(operation.getBalance().setScale(2, RoundingMode.DOWN));
            printer.println(lineStatement);
        });
    }
}
