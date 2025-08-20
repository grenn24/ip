public class Deadline extends Task {
    private final String deadline;
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by %s)", super.isCompleted() ? "X" : " ", super.getDescription(), deadline);
    }
}
