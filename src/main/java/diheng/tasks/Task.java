package diheng.tasks;

public class Task {
    /**
     * The description of the task.
     */
    private final String description;
    /**
     * The completion status of the task.
     */
    private boolean isCompleted;

    public Task(String description) {
        this.description = description;
    }

    public Task(String description, boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isCompleted ? "X" : " ", description);
    }
}
