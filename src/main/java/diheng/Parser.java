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
     * The storage to be used by the parse.
     */
    private final Storage storage;

    public Parser(TaskList tasklist, Storage storage) {
        this.tasklist = tasklist;
        this.storage = storage;
    }

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
            case LOAD:
                return storage.setFilepath(commandArgs);
            default:
                throw new DiHengException(
                        "Unknown command: " + parts[0],
                        "The supported commands are: list, mark, unmark, todo, event, deadline, load, bye"
                );
        }
    }

    private String handleMark(String args) throws DiHengException {
        assert args != null : "handleMark args should not be null";
        String[] parts = args.split("\\s+");
        int[] indexes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            indexes[i] = parseTaskIndex(parts[i]);
        }
        return tasklist.markTasks(indexes);
    }

    private String handleUnmark(String args) throws DiHengException {
        assert args != null : "handleUnmark args should not be null";
        String[] parts = args.split("\\s+");
        int[] indexes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            indexes[i] = parseTaskIndex(parts[i]);
        }
        return tasklist.unmarkTasks(indexes);
    }

    private String handleDelete(String args) throws DiHengException {
        assert args != null : "handleDelete args should not be null";
        int index = parseTaskIndex(args);
        return tasklist.delete(index - 1);
    }

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
