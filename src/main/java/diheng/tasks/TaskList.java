package diheng.tasks;

import java.util.ArrayList;
import java.util.List;

import diheng.DiHengException;
import diheng.enums.Command;

public class TaskList {

    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void listTasks() {
        System.out.println("Here are the pending tasks:");
        for (int i = 0; i < this.tasks.size(); i++) {
            Task task = this.tasks.get(i);
            System.out.printf("%d.%s\n", i + 1, task);
        }

    }

    public void markTask(int index) throws DiHengException {
        Task task = this.tasks.get(index);
        task.setCompleted(true);

        System.out.println("Nice! I've marked this task as done:");
        System.out.printf(" %d.%s\n", index + 1, task);
    }

    public void unmarkTask(int index) throws DiHengException {
        Task task = this.tasks.get(index);
        task.setCompleted(false);

        System.out.println("OK, I've marked this task as not done yet:");
        System.out.printf(" %d.%s\n", index + 1, task);
    }

    public void deleteTask(int index) throws DiHengException {
        Task task = this.tasks.get(index);
        this.tasks.remove(index);

        System.out.println("Noted. I've removed this task:");
        System.out.printf(" %d.%s\n", index + 1, task);
        System.out.printf("Now you have %d tasks in the list.\n", this.tasks.size());
    }

    public void clearTasks() throws DiHengException {
        tasks.clear();
        System.out.println("All tasks have been cleared.");
    }

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

    public List<Task> getTasks() {
        return tasks;
    }
}
