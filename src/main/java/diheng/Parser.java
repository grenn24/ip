package diheng;

import diheng.enums.Command;
import diheng.exceptions.DiHengException;
import diheng.tasks.TaskList;

public class Parser {

    private final TaskList tasklist;

    public Parser(TaskList tasklist) {
        this.tasklist = tasklist;
    }

    /**
     * Parses and executes a command.
     *
     * @param command the command to execute
     * @param commandArgs arguments for the command, if any
     * @throws DiHengException if input is invalid
     */
    public boolean parse(String input) throws DiHengException {
        String[] parts = input.split(" ", 2);
        Command command;
        try {
            command = Command.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            command = Command.UNKNOWN;
        }
        String commandArgs = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case BYE:
                return handleBye();
            case LIST:
                tasklist.listTasks();
                break;
            case MARK:
                handleMark(commandArgs);
                break;
            case UNMARK:
                handleUnmark(commandArgs);
                break;
            case DELETE:
                handleDelete(commandArgs);
                break;
            case CLEAR:
                tasklist.clearTasks();
                break;
            case TODO, EVENT, DEADLINE:
                handleCreateTask(command, commandArgs);
                break;
            default:
                throw new DiHengException(
                        "Unknown command",
                        "The supported commands are: list, mark, unmark, todo, event, deadline, bye"
                );
        }
        return false;
    }

    private boolean handleBye() {
        System.out.println("Goodbye!");
        return true;
    }

    private void handleMark(String args) throws DiHengException {
        int index = parseTaskIndex(args, "mark");
        tasklist.markTask(index - 1);
    }

    private void handleUnmark(String args) throws DiHengException {
        int index = parseTaskIndex(args, "unmark");
        tasklist.unmarkTask(index - 1);
    }

    private void handleDelete(String args) throws DiHengException {
        int index = parseTaskIndex(args, "delete");
        tasklist.deleteTask(index - 1);
    }

    private void handleCreateTask(Command command, String args) throws DiHengException {
        if (args.isEmpty()) {
            throw new DiHengException(
                    "Missing task description",
                    "Please provide the description of the task."
            );
        }
        tasklist.createTask(command, args);
    }

    private int parseTaskIndex(String args, String action) throws DiHengException {
        if (args.isEmpty()) {
            throw new DiHengException(
                    "Missing task index",
                    "Please provide the index of the task to " + action + "."
            );
        }
        try {
            return Integer.parseInt(args);
        } catch (NumberFormatException e) {
            throw new DiHengException(
                    "Invalid task index",
                    "Task index must be a number."
            );
        }
    }
}
