package diheng.tasks;

/**
 * A class representing an event task.
 */
public class Event extends Task {
    private final String start;
    private final String end;

    /**
     * Constructor for Event task.
     *
     * @param description Description of the event.
     * @param start       Start time of the event.
     * @param end         End time of the event.
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
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
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (from: %s to: %s)", super.isCompleted() ? "X" : " ", super.getDescription(), start, end);
    }
}
