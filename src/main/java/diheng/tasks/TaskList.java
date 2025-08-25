package diheng.tasks;

import java.util.ArrayList;
import java.util.List;

import diheng.enums.Command;
import diheng.exceptions.DiHengException;

/**
 * Manages a list of tasks, providing functionalities to add, list, mark, unmark,
 * delete, clear, and create tasks.
 */
public class TaskList {

    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor for TaskList with a list of tasks.
     * @param tasks
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Lists all tasks in the task list according to their string representation.
     */
    public void listTasks() {
        System.out.println("Here are the pending tasks:");
        for (int i = 0; i < this.tasks.size(); i++) {
            Task task = this.tasks.get(i);
            System.out.printf("%d.%s\n", i + 1, task);
        }

    }

    /**
     * Marks a task as completed based on its index in the task list.
     * @param index the index of the task to be marked as completed (0-based)
     */
    public void markTask(int index) {
        Task task = this.tasks.get(index);
        task.setCompleted(true);

        System.out.println("Nice! I've marked this task as done:");
        System.out.printf(" %d.%s\n", index + 1, task);
    }

    /**
     * Unmarks a task as not completed based on its index in the task list.
     * @param index the index of the task to be unmarked as not completed (0-based)
     */
    public void unmarkTask(int index) {
        Task task = this.tasks.get(index);
        task.setCompleted(false);

        System.out.println("OK, I've marked this task as not done yet:");
        System.out.printf(" %d.%s\n", index + 1, task);
    }

    /**
     * Deletes a task from the task list based on its index.
     * @param index the index of the task to be deleted (0-based)
     */
    public void deleteTask(int index) {
        Task task = this.tasks.get(index);
        this.tasks.remove(index);

        System.out.println("Noted. I've removed this task:");
        System.out.printf(" %d.%s\n", index + 1, task);
        System.out.printf("Now you have %d tasks in the list.\n", this.tasks.size());
    }

    /**
     * Clears all tasks from the task list.
     */
    public void clearTasks() {
        tasks.clear();
        System.out.println("All tasks have been cleared.");
    }

    /**
     * Creates a new task based on the command type and arguments, adds it to the task list,
     * and prints confirmation messages.
     * @param type the type of task to create (TODO, EVENT, DEADLINE)
     * @param commandArgs the arguments for creating the task
     * @return the created Task object
     * @throws DiHengException if there are issues with the command arguments
     */
    public Task createTask(Command type, String commandArgs) throws DiHengException {
        Task currTask;
        switch (type) {
            case TODO ->
                currTask = new ToDo(commandArgs);

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

            default ->
                throw new DiHengException(
                        "Unknown command",
                        "The supported commands are: list, mark, unmark, todo, event, deadline, bye"
                );
        }
        tasks.add(currTask);
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + currTask);
        System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
        return currTask;
    }
}
