
public class Event extends Task {

    private final String start;
    private final String end;

    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

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
