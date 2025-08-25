package diheng.tasks;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public ToDo(String description, boolean isCompleted) {
        super(description, isCompleted);
    }

    @Override
    public String toString() {
        return String.format("[T][%s] %s", super.isCompleted() ? "X" : " ", super.getDescription());
    }
}
