package diheng.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class representing a deadline task.
 */
public class Deadline extends Task {
    private final LocalDateTime deadline;
    private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Constructor for Deadline without completion status.
     *
     * @param description The description of the deadline task.
     * @param deadline    The deadline date and time in "dd/MM/yyyy HH:mm" format.
     */
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = LocalDateTime.parse(deadline, DATE_TIME_FORMAT
        );
    }

    /**
     * Constructor for Deadline with completion status.
     *
     * @param description The description of the deadline task.
     * @param deadline    The deadline date and time in "dd/MM/yyyy HH:mm" format.
     * @param isCompleted The completion status of the task.
     */
    public Deadline(String description, String deadline, boolean isCompleted) {
        super(description, isCompleted);
        this.deadline = LocalDateTime.parse(deadline, DATE_TIME_FORMAT);
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", super.isCompleted() ? "X" : " ", super.getDescription(), this.deadline.format(DATE_TIME_FORMAT));
    }
}
