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
     *
     * @param input the raw user input
     * @return the message to be displayed to the user by UI class
     * @throws DiHengException if the user input is invalid
     */
    public String parse(String input) throws DiHengException {
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
                return "Goodbye!";
            case LIST:
                return tasklist.list();
            case MARK:
                return handleMark(commandArgs);
            case UNMARK:
                return handleUnmark(commandArgs);
            case DELETE:
                return handleDelete(commandArgs);
            case CLEAR:
                return tasklist.clear();
            case FIND:
                return tasklist.find(commandArgs);
            case TODO, EVENT, DEADLINE:
                return tasklist.add(command, commandArgs);
            default:
                throw new DiHengException(
                        "Unknown command: " + parts[0],
                        "The supported commands are: list, mark, unmark, todo, event, deadline, bye"
                );
        }

    }


    /**
     * Handles the MARK command.
     *
     * @param args the arguments for the command
     * @throws DiHengException if the arguments are invalid
     */
    private String handleMark(String args) throws DiHengException {
        int index = parseTaskIndex(args);
        return tasklist.markTask(index - 1);
    }

    /**
     * Handles the UNMARK command.
     *
     * @param args the arguments for the command
     * @throws DiHengException if the arguments are invalid
     */
    private String handleUnmark(String args) throws DiHengException {
        int index = parseTaskIndex(args);
        return tasklist.unmarkTask(index - 1);
    }

    /**
     * Handles the DELETE command.
     *
     * @param args the arguments for the command
     * @throws DiHengException if the arguments are invalid
     */
    private String handleDelete(String args) throws DiHengException {
        int index = parseTaskIndex(args);
        return tasklist.delete(index - 1);
    }


    /**
     * Parse the task index from the command arguments.
     *
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
