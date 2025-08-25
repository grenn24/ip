package diheng;

import diheng.enums.Command;
import diheng.exceptions.DiHengException;
import diheng.tasks.TaskList;

/**
 * A class that parses and executes commands inside a raw user input.
 */
public class Parser {

    private final TaskList tasklist;

    /**
     * Constructor for Parser with a task list.
     * @param tasklist
     */
    public Parser(TaskList tasklist) {
        this.tasklist = tasklist;
    }

    /**
     * Parses and executes a command based on raw user input.
     * @param input the raw user input
     * @throws DiHengException if the user input is invalid
     */
    public boolean parse(String input) throws DiHengException {
        String[] parts = input.split(" ", 2);
        Command command;
        if (parts.length == 0) {
            throw new DiHengException("No command provided", "Please provide a command followed by an argument");
        }
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
                        "Unknown command: " + parts[0],
                        "The supported commands are: list, mark, unmark, todo, event, deadline, bye"
                );
        }
        return false;
    }

    /**
     * Handles the BYE command.
     * @return true to terminate the program
     */
    private boolean handleBye() {
        System.out.println("Goodbye!");
        return true;
    }

    /**
     * Handles the MARK command.
     * @param args the arguments for the command
     * @throws DiHengException if the arguments are invalid
     */
    private void handleMark(String args) throws DiHengException {
        int index = parseTaskIndex(args);
        tasklist.markTask(index - 1);
    }

    /**
     * Handles the UNMARK command.
     * @param args the arguments for the command
     * @throws DiHengException if the arguments are invalid
     */
    private void handleUnmark(String args) throws DiHengException {
        int index = parseTaskIndex(args);
        tasklist.unmarkTask(index - 1);
    }

    /**
     * Handles the DELETE command.
     * @param args the arguments for the command
     * @throws DiHengException if the arguments are invalid
     */
    private void handleDelete(String args) throws DiHengException {
        int index = parseTaskIndex(args);
        tasklist.deleteTask(index - 1);
    }

    /**
     * Handles the command for creating a new task.
     * @param args the arguments for the command
     * @throws DiHengException if the arguments are invalid
     */
    private void handleCreateTask(Command command, String args) throws DiHengException {
        if (args.isEmpty()) {
            throw new DiHengException(
                    "Missing task description",
                    "Please provide the description of the task."
            );
        }
        tasklist.createTask(command, args);
    }

    /**
     * Parse the task index from the command arguments.
     * @param args the arguments for the command
     * @throws DiHengException if the arguments are invalid
     */
    private int parseTaskIndex(String args) throws DiHengException {
        if (args.isEmpty()) {
            throw new DiHengException(
                    "Missing task index",
                    "Please provide the index of the task to operate on."
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
