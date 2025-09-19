package diheng.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A class representing an event task.
 *
 * @see Task
 */
public class Event extends Task {
    private final LocalDate start;
    private final LocalDate end;
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructor for Event task.
     *
     * @param description Description of the event.
     * @param start       Start time of the event.
     * @param end         End time of the event.
     */
    public Event(String description, String start, String end) {
        super(description);

        this.start = LocalDate.parse(start, DATE_TIME_FORMAT );
        this.end = LocalDate.parse(end, DATE_TIME_FORMAT
        );
    }

    /**
     * Constructor for Event task with completion status.
     *
     * @param description Description of the event.
     * @param start       Start time of the event.
     * @param end         End time of the event.
     * @param isCompleted Completion status of the event.
     */
    public Event(String description, String start, String end, boolean isCompleted) {
        super(description, isCompleted);
        this.start = LocalDate.parse(start, DATE_TIME_FORMAT);
        this.end = LocalDate.parse(end, DATE_TIME_FORMAT);
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to: %s)", super.isCompleted() ? "X" : " ",
                super.getDescription(), start.format(DATE_TIME_FORMAT), end.format(DATE_TIME_FORMAT));
    }
}
