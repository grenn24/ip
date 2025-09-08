package diheng;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import diheng.exceptions.DiHengException;
import diheng.tasks.Deadline;
import diheng.tasks.Event;
import diheng.tasks.Task;
import diheng.tasks.ToDo;

/**
 * A class that handles the import and export of tasks.
 */
public class Storage {

    /**
     * Logger for Storage class.
     */
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    /**
     * Marker for ToDo tasks.
     */
    private static final String TODO_MARKER = "[T]";
    /**
     * Marker for Event tasks.
     */
    private static final String EVENT_MARKER = "[E]";
    /**
     * Marker for Deadline tasks.
     */
    private static final String DEADLINE_MARKER = "[D]";

    /**
     * Default filepath for storage.
     */
    private final String filepath;

    public Storage(String filepath) {
        assert filepath != null && !filepath.isEmpty() : "Filepath must not be null or empty";
        this.filepath = filepath;
    }

    public void saveTasks(List<Task> tasks) throws DiHengException {
        assert tasks != null : "Tasks list must not be null";

        Path path = Paths.get(filepath);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            throw new DiHengException("Error saving tasks",
                    "Could not create directories for file: " + filepath);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            AtomicInteger counter = new AtomicInteger(1);
            tasks.stream()
                    .peek(task -> {
                        assert task != null : "Task in list cannot be null";
                    })
                    .forEach(task -> {
                        try {
                            writer.write(counter.getAndIncrement() + "." + task.toString());
                            writer.newLine();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (IOException | UncheckedIOException e) {
            throw new DiHengException("Error saving tasks",
                    "An error occurred while saving tasks to disk:\n" + e.getMessage());
        }
    }

    @SuppressWarnings("checkstyle:NeedBraces")
    public List<Task> loadTasks() throws DiHengException {
        Path path = Paths.get(filepath);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try {
            return Files.lines(path)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(this::parseTaskFromString)
                    .flatMap(Optional::stream)
                    .map(task -> (Task) task)
                    .toList();
        } catch (IOException e) {
            throw new DiHengException("Error loading tasks",
                    "An error occurred while loading tasks from disk:\n" + e.getMessage());
        }
    }

    private Optional<? extends Task> parseTaskFromString(String line) {
        try {
            int dotIndex = line.indexOf(".");
            if (dotIndex == -1) {
                logger.warning("Invalid task format -> " + line);
                return Optional.empty();
            }

            String taskPart = line.substring(dotIndex + 1).trim();
            boolean isCompleted = line.substring(dotIndex + 4, dotIndex + 7).equals("[X]");
            Optional<Task> task;
            if (taskPart.startsWith(TODO_MARKER)) {
                return parseToDo(taskPart, isCompleted);
            } else if (taskPart.startsWith(EVENT_MARKER)) {
                return parseEvent(taskPart, isCompleted);
            } else if (taskPart.startsWith(DEADLINE_MARKER)) {
                return parseDeadline(taskPart, isCompleted);
            } else {
                logger.warning("Unknown task type -> " + line);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.warning("Could not parse line -> " + line + " Exception: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<ToDo> parseToDo(String taskPart, boolean isCompleted) {
        String desc = taskPart.substring(6).trim();
        assert !desc.isEmpty() : "ToDo description should not be empty";
        return Optional.of(new ToDo(desc, isCompleted));
    }

    private Optional<Event> parseEvent(String taskPart, boolean isCompleted) {
        int fromIndex = taskPart.indexOf("(from:");
        int toIndex = taskPart.indexOf("to:", fromIndex);
        int endIndex = taskPart.indexOf(")", toIndex);

        if (fromIndex == -1 || toIndex == -1 || endIndex == -1) {
            logger.warning("Invalid Event format -> " + taskPart);
            return Optional.empty();
        }

        String desc = taskPart.substring(6, fromIndex).trim();
        String start = taskPart.substring(fromIndex + 6, toIndex).trim();
        String end = taskPart.substring(toIndex + 3, endIndex).trim();
        return Optional.of(new Event(desc, start, end, isCompleted));
    }

    private Optional<Deadline> parseDeadline(String taskPart, boolean isCompleted) {
        int byIndex = taskPart.indexOf("(by:");
        int endIndex = taskPart.indexOf(")", byIndex);

        if (byIndex == -1 || endIndex == -1) {
            logger.warning("Invalid Deadline format -> " + taskPart);
            return Optional.empty();
        }

        String desc = taskPart.substring(6, byIndex).trim();
        String by = taskPart.substring(byIndex + 4, endIndex).trim();
        return Optional.of(new Deadline(desc, by, isCompleted));
    }
}
