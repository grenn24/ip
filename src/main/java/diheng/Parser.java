package diheng;

import diheng.enums.Command;
import diheng.exceptions.DiHengException;
import diheng.tasks.TaskList;

import java.util.Arrays;

/**
 * A class that parses and executes commands from raw user input.
 */
public class Parser {

    private final TaskList tasklist;
    private final Storage storage;

    public Parser(TaskList tasklist, Storage storage) {
        this.tasklist = tasklist;
        this.storage = storage;
    }

    /**
     * Parses the user input and executes the corresponding command.
     *
     * @param input raw user input
     * @return the result of executing the command
     * @throws DiHengException if the command is invalid or fails
     */
    public String parse(String input) throws DiHengException {
        if (input == null || input.isBlank()) {
            throw new DiHengException("No command provided", "Please provide a valid command");
        }

        String[] parts = input.trim().split("\\s+", 2);
        Command command = parseCommand(parts[0]);
        String args = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
            case BYE:
                return "Goodbye!";
            case LIST:
                return tasklist.list();
            case MARK:
                return handleMultipleTasks(args, tasklist::markTasks);
            case UNMARK:
                return handleMultipleTasks(args, tasklist::unmarkTasks);
            case DELETE:
                return tasklist.delete(parseSingleTaskIndex(args));
            case CLEAR:
                return tasklist.clear();
            case FIND:
                return tasklist.find(args);
            case TODO, EVENT, DEADLINE:
                return tasklist.add(command, args);
            case LOAD:
                return storage.setFilepath(args);
            case HELP:
                return "Hey there! \uD83D\uDE0E Here\u2019s what I can do for you:\n"
                        + "- list: see all your tasks \uD83D\uDCCB\n"
                        + "- mark/unmark: track your progress \u2705\u274C\n"
                        + "- todo: add a to-do \uD83D\uDCDD\n"
                        + "- event: schedule an event \uD83D\uDDD3\uFE0F\n"
                        + "- deadline: set a deadline \u23F0\n"
                        + "- load: switch files \uD83D\uDCBE\n"
                        + "- bye: say goodbye \uD83D\uDC4B";
            default:
                throw new DiHengException(
                        "Unknown command: " + parts[0],
                        "Supported commands: list, mark, unmark, todo, event, deadline, load, bye"
                );
        }
    }

    private Command parseCommand(String input) {
        try {
            return Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Command.UNKNOWN;
        }
    }

    private int parseSingleTaskIndex(String arg) throws DiHengException {
        if (arg == null || arg.isBlank()) {
            throw new DiHengException("Missing task index", "Please provide the index of the task to operate on.");
        }
        try {
            return Integer.parseInt(arg) - 1; // convert to 0-based index
        } catch (NumberFormatException e) {
            throw new DiHengException("Invalid task index", "Task index must be a number.");
        }
    }

    private String handleMultipleTasks(String args, TaskOperation operation) throws DiHengException {
        if (args.isBlank()) {
            throw new DiHengException("Missing task indices", "Please provide one or more task indices.");
        }
        int[] indexes = Arrays.stream(args.split("\\s+"))
                .mapToInt(this::safeParseIndex)
                .toArray();
        return operation.apply(indexes);
    }

    private int safeParseIndex(String s) {
        try {
            return Integer.parseInt(s) - 1;
        } catch (NumberFormatException e) {
            throw new RuntimeException(new DiHengException(
                    "Invalid task index: " + s,
                    "Task index must be a number."
            ));
        }
    }

    @FunctionalInterface
    private interface TaskOperation {
        String apply(int[] indexes) throws DiHengException;
    }
}
