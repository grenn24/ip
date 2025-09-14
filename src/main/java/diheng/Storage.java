package diheng;

import diheng.exceptions.DiHengException;
import diheng.tasks.Deadline;
import diheng.tasks.Event;
import diheng.tasks.Task;
import diheng.tasks.ToDo;

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

/**
 * Handles the import and export of tasks to and from the disk.
 */
public class Storage {

    private static final Logger logger = Logger.getLogger(Storage.class.getName());

    private static final String TODO_MARKER = "[T]";
    private static final String EVENT_MARKER = "[E]";
    private static final String DEADLINE_MARKER = "[D]";

    private String filepath;

    public Storage(String filepath) {
        assert filepath != null && !filepath.isEmpty() : "Filepath must not be null or empty";
        setFilepathInternal(filepath);
    }

    public void saveTasks(List<Task> tasks) throws DiHengException {
        assert tasks != null : "Tasks list must not be null";

        Path path = Paths.get(filepath);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            throw new DiHengException("Error saving tasks", "Could not create directories for file: " + filepath);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            AtomicInteger counter = new AtomicInteger(1);
            for (Task task : tasks) {
                assert task != null : "Task in list cannot be null";
                writer.write(counter.getAndIncrement() + ". " + task);
                writer.newLine();
            }
        } catch (IOException | UncheckedIOException e) {
            throw new DiHengException("Error saving tasks", "Failed to save tasks: " + e.getMessage());
        }
    }

    public List<Task> loadTasks() throws DiHengException {
        Path path = Paths.get(filepath);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try {
            List<Task> tasks = new ArrayList<>();
            Files.lines(path)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .forEach(line -> parseTaskFromLine(line).ifPresent(tasks::add));
            return tasks;
        } catch (IOException e) {
            throw new DiHengException("Error loading tasks", "Failed to load tasks: " + e.getMessage());
        }
    }

    private Optional<Task> parseTaskFromLine(String line) {
        try {
            int dotIndex = line.indexOf(".");
            if (dotIndex == -1) {
                logger.warning("Invalid task format -> " + line);
                return Optional.empty();
            }

            String taskPart = line.substring(dotIndex + 1).trim();

            // get the completion marker dynamically
            boolean isCompleted = taskPart.contains("[X]");

            if (taskPart.startsWith(TODO_MARKER)) {
                return parseToDo(taskPart, isCompleted);
            }
            if (taskPart.startsWith(EVENT_MARKER)) {
                return parseEvent(taskPart, isCompleted);
            }
            if (taskPart.startsWith(DEADLINE_MARKER)) {
                return parseDeadline(taskPart, isCompleted);
            }

            logger.warning("Unknown task type -> " + line);
            return Optional.empty();
        } catch (Exception e) {
            logger.warning("Failed to parse line -> " + line + " Exception: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<Task> parseToDo(String taskPart, boolean isCompleted) {
        // Remove the [T][ ] or [T][X] prefix
        String desc = taskPart.substring(TODO_MARKER.length()).trim();
        if (desc.startsWith("[ ]") || desc.startsWith("[X]")) {
            desc = desc.substring(3).trim(); // remove the completion marker
        }
        return Optional.of(new ToDo(desc, isCompleted));
    }

    private Optional<Task> parseEvent(String taskPart, boolean isCompleted) {
        int fromIndex = taskPart.indexOf("(from:");
        int toIndex = taskPart.indexOf("to:", fromIndex);
        int endIndex = taskPart.indexOf(")", toIndex);

        if (fromIndex == -1 || toIndex == -1 || endIndex == -1) {
            logger.warning("Invalid Event format -> " + taskPart);
            return Optional.empty();
        }

        String desc = taskPart.substring(EVENT_MARKER.length()).trim();
        if (desc.startsWith("[ ]") || desc.startsWith("[X]")) {
            desc = desc.substring(3).trim();
        }

        String start = taskPart.substring(fromIndex + 6, toIndex).trim();
        String end = taskPart.substring(toIndex + 3, endIndex).trim();

        return Optional.of(new Event(desc, start, end, isCompleted));
    }

    private Optional<Task> parseDeadline(String taskPart, boolean isCompleted) {
        int byIndex = taskPart.indexOf("(by:");
        int endIndex = taskPart.indexOf(")", byIndex);

        if (byIndex == -1 || endIndex == -1) {
            logger.warning("Invalid Deadline format -> " + taskPart);
            return Optional.empty();
        }

        String desc = taskPart.substring(DEADLINE_MARKER.length()).trim();
        if (desc.startsWith("[ ]") || desc.startsWith("[X]")) {
            desc = desc.substring(3).trim();
        }

        String by = taskPart.substring(byIndex + 4, endIndex).trim();
        return Optional.of(new Deadline(desc, by, isCompleted));
    }

    public String setFilepath(String filepath) throws DiHengException {
        if (filepath == null || filepath.isBlank()) {
            throw new DiHengException("Invalid filepath", "Filepath must not be empty or null.");
        }
        setFilepathInternal(filepath);
        return "File path changed successfully!";
    }

    private void setFilepathInternal(String filepath) {
        this.filepath = filepath;
        Path path = Paths.get(filepath);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create directories for file: " + filepath);
        }
    }
}
