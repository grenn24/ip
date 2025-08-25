
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private final LocalDateTime deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = LocalDateTime.parse(deadline);
    }

    public Deadline(String description, String deadline, boolean isCompleted) {
        super(description, isCompleted);
        this.deadline = LocalDateTime.parse(deadline);
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by %s)", super.isCompleted() ? "X" : " ", super.getDescription(), deadline.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")));
    }
}
