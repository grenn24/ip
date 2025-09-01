package diheng.tasks;

import java.util.ArrayList;
import java.util.List;

import diheng.enums.Command;
import diheng.exceptions.DiHengException;

/**
 * Manages a list of tasks, providing functionalities to add, list, mark, unmark,
 * delete, clear, and create tasks.
 *
 * @see Task
 */
public class TaskList {
    /**
     * The list of tasks.
     */
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor for TaskList with a list of tasks.
     *
     * @param tasks the list of tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Lists all tasks in the task list according to their string representation.
     *
     * @return a string representation of the tasks
     */
    public String list() {
        if (tasks.isEmpty()) {
            return "No tasks available.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the pending tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            sb.append(String.format("%d.%s\n", i + 1, task));
        }
        return sb.toString().trim();
    }


    /**
     * Marks one or more tasks as completed.
     *
     * @param indexes the indexes of the tasks to be marked as completed (0-based)
     * @return a string summarizing which tasks have been marked
     * @throws IndexOutOfBoundsException if any index is invalid
     */
    public String markTasks(int... indexes) {
        StringBuilder sb = new StringBuilder();
        for (int index : indexes) {
            Task task = this.tasks.get(index); // may throw IndexOutOfBoundsException
            task.setCompleted(true);
            sb.append("Nice! I've marked this task as done:\n");
            sb.append(String.format(" %d.%s\n", index + 1, task));
        }
        return sb.toString().trim();
    }


    /**
     * Marks one or more tasks as not completed.
     *
     * @param indexes the indexes of the tasks to unmark (0-based)
     * @return a message summarizing which tasks have been unmarked
     * @throws IndexOutOfBoundsException if any index is invalid
     */
    public String unmarkTasks(int... indexes) {
        StringBuilder sb = new StringBuilder();
        for (int index : indexes) {
            Task task = this.tasks.get(index);  // may throw IndexOutOfBoundsException
            task.setCompleted(false);
            sb.append("OK, I've marked this task as not done yet:\n");
            sb.append(String.format(" %d.%s\n", index + 1, task));
        }
        return sb.toString().trim();
    }

    /**
     * Deletes a task from the task list based on its index.
     *
     * @param index the index of the task to be deleted (0-based)
     * @return a string to be printed by UI
     */
    public String delete(int index) {
        Task task = this.tasks.get(index);
        this.tasks.remove(index);

        StringBuilder sb = new StringBuilder();
        sb.append("Noted. I've removed this task:\n");
        sb.append(String.format(" %d.%s\n", index + 1, task));
        sb.append(String.format("Now you have %d tasks in the list", this.tasks.size()));
        return sb.toString().trim();
    }


    /**
     * Finds tasks in the task list whose descriptions contain the input string
     * (case-insensitive) and returns a string representation of the matching tasks.
     *
     * @param input the string to search for in the task descriptions
     * @return a string representation of the matching tasks, or "No tasks found." if no
     * matching tasks are found
     */
    public String find(String input) {
        if (input == null || input.isEmpty()) {
            return "No tasks found.";
        }
        List<Task> tasks = this.tasks.stream()
                .filter(task -> task.getDescription()
                        .toLowerCase()
                        .contains(input.toLowerCase()))
                .toList();
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            sb.append(String.format("%d.%s\n", i + 1, task));
        }
        return sb.toString().trim();
    }

    /**
     * Clears all tasks from the task list.
     *
     * @return a string message indicating that all tasks have been cleared
     */
    public String clear() {
        tasks.clear();
        return "All tasks have been cleared.\n";
    }

    /**
     * Creates a new task based on the command type and arguments, adds it to the task list,
     * and prints confirmation messages.
     *
     * @param type        the type of task to create (TODO, EVENT, DEADLINE)
     * @param commandArgs the arguments for creating the task
     * @return the created Task object
     * @throws DiHengException if there are issues with the command arguments
     */
    public String add(Command type, String commandArgs) throws DiHengException {
        if (commandArgs.isEmpty()) {
            throw new DiHengException(
                    "Missing task description",
                    "Please provide the description of the task."
            );
        }
        Task currTask;
        switch (type) {
            case TODO -> currTask = new ToDo(commandArgs);

            case EVENT -> {
                String[] parts = commandArgs.split("/from|/to");
                if (parts.length < 3) {
                    throw new DiHengException("Missing event start and end times",
                            "Events must have /from and /to times.");
                }
                String desc = parts[0].trim();
                String start = parts[1].trim();
                String end = parts[2].trim();
                currTask = new Event(desc, start, end);
            }

            case DEADLINE -> {
                String[] parts = commandArgs.split("/by");
                if (parts.length < 2) {
                    throw new DiHengException("Missing deadline time",
                            "Deadlines must have a /by time.");
                }
                String desc = parts[0].trim();
                String by = parts[1].trim();
                currTask = new Deadline(desc, by);
            }

            default -> throw new DiHengException(
                    "Unknown command",
                    "The supported commands are: list, mark, unmark, todo, event, deadline, bye"
            );
        }
        tasks.add(currTask);

        StringBuilder sb = new StringBuilder();
        sb.append("Got it. I've added this task:\n");
        sb.append(String.format(" %s\n", currTask));
        sb.append(String.format(" Now you have %d tasks in the list.", tasks.size()));
        return sb.toString().trim();
    }
}
