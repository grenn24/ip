package diheng.tasks;

/**
 * A class representing a todo task.
 */
public class ToDo extends Task {
    /**
     * Constructor for ToDo task.
     * @param description Description of the todo task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Constructor for ToDo task with completion status.
     * @param description Description of the todo task.
     * @param isCompleted Completion status of the todo task.
     */
    public ToDo(String description, boolean isCompleted) {
        super(description, isCompleted);
    }

    @Override
    public String toString() {
        return String.format("[T][%s] %s", super.isCompleted() ? "X" : " ", super.getDescription());
    }
}
