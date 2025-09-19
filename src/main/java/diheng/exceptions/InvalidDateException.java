package diheng.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvalidDateException extends DiHengException {
    public InvalidDateException(DateTimeFormatter formatter) {
        super( "\u26A0 Invalid date format",  "Please enter the date in the following format: " +
                formatter
                .format(LocalDateTime.now()));
    }
}
