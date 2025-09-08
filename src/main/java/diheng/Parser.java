package diheng;

import diheng.enums.Command;
import diheng.exceptions.DiHengException;
import diheng.tasks.TaskList;

/**
 * A class that parses and executes commands inside a raw user input.
 */
public class Parser {
    /**
     * The task list to be used by the parser.
     */
    private final TaskList tasklist;

    /**
     * Constructor for Parser with a task list.
     *
     * @param tasklist the task list to be used by the parser
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
        assert input != null && !input.isEmpty() : "Input should not be null or empty";
        String[] parts = input.split(" ", 2);
        if (parts.length == 0) {
            throw new DiHengException("No command provided", "Please provide a valid command");
        }
        Command command;
        try {
            command = Command.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            command = Command.UNKNOWN;
        }
        String commandArgs = parts.length > 1 ? parts[1] : "";
        assert commandArgs != null : "Command args should not be null";

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
     * @return the message to be displayed to the user by UI class
     * @throws DiHengException if the arguments are invalid
     */
    private String handleMark(String args) throws DiHengException {
        String[] parts = args.split("\\s+");
        int[] indexes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            indexes[i] = parseTaskIndex(parts[i]);
        }
        return tasklist.markTasks(indexes);
    }

    /**
     * Handles the UNMARK command for one or more tasks.
     *
     * @param args the arguments for the command (space-separated task indexes)
     * @return a message summarizing which tasks were unmarked
     * @throws DiHengException if any argument is invalid
     */
    private String handleUnmark(String args) throws DiHengException {
        String[] parts = args.split("\\s+");
        int[] indexes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            indexes[i] = parseTaskIndex(parts[i]);
        }
        return tasklist.unmarkTasks(indexes);
    }

    /**
     * Handles the DELETE command.
     *
     * @param args the arguments for the command
     * @return the message to be displayed to the user by UI class
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
     * @return the message to be displayed to the user by UI class
     * @throws DiHengException if the arguments are invalid
     */
    private int parseTaskIndex(String args) throws DiHengException {
        assert args != null : "Task index args should not be null";
        if (args.isEmpty()) {
            throw new DiHengException(
                    "Missing task index",
                    "Please provide the index of the task to operate on."
            );
        }
        try {
            return Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            throw new DiHengException(
                    "Invalid task index",
                    "Task index must be a number."
            );
        }
    }
}
