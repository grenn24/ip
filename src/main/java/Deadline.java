
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private final LocalDate deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = LocalDate.parse(deadline);
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by %s)", super.isCompleted() ? "X" : " ", super.getDescription(), deadline.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
    }
}
