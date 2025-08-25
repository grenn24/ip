package diheng.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private final LocalDateTime deadline;
    private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = LocalDateTime.parse(deadline, DATE_TIME_FORMAT
        );
    }

    public Deadline(String description, String deadline, boolean isCompleted) {
        super(description, isCompleted);
        this.deadline = LocalDateTime.parse(deadline, DATE_TIME_FORMAT);
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", super.isCompleted() ? "X" : " ", super.getDescription(), this.deadline.format(DATE_TIME_FORMAT));
    }
}
