package diheng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * The filepath of the storage file.
     */
    private final String filepath;

    /**
     * Constructor for Storage with a filepath.
     *
     * @param filepath
     */
    public Storage(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Save a list of task to the file specified by filepath.
     *
     * @param tasks the list of tasks to be saved
     * @throws DiHengException if an io exception occurs
     */
    public void saveTasks(List<Task> tasks) throws DiHengException {
        File file = new File(filepath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            boolean isSuccessful = parent.mkdirs();
            if (!isSuccessful) {
                throw new DiHengException("Error saving tasks", "Try again later");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                writer.write((i + 1) + "." + task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new DiHengException("Error saving tasks",
                    "An error occurred while saving tasks to disk:\n" + e.getMessage());
        }
    }

    /**
     * Load tasks from the file specified by filepath.
     *
     * @return a list of tasks loaded from the file
     * @throws DiHengException if an io exception occurs
     */
    public List<Task> loadTasks() throws DiHengException {
        File file = new File(filepath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<Task> loadedTasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task task = parseTaskFromString(line);
                loadedTasks.add(task);
            }
        } catch (IOException e) {
            throw new DiHengException("Error loading tasks",
                    "An error occurred while loading tasks from disk:\n" + e.getMessage());
        }

        return loadedTasks;
    }

    /**
     * Convert the string representation of a task stored in file to a Task object.
     *
     * @param line the string representation of a task stored in file
     * @return a task
     */
    private Task parseTaskFromString(String line) {
        try {
            int dotIndex = line.indexOf(".");
            if (dotIndex == -1) {
                System.out.println("Warning: Invalid task format -> " + line);
                return null;
            }

            String taskPart = line.substring(dotIndex + 1).trim();
            boolean isCompleted = line.substring(dotIndex + 4, dotIndex + 7).equals("[X]");
            if (taskPart.startsWith("[T]")) {
                String desc = taskPart.substring(6).trim();
                return new ToDo(desc, isCompleted);
            } else if (taskPart.startsWith("[E]")) {
                int fromIndex = taskPart.indexOf("(from:");
                int toIndex = taskPart.indexOf("to:", fromIndex);
                int endIndex = taskPart.indexOf(")", toIndex);
                if (fromIndex == -1 || toIndex == -1 || endIndex == -1) {
                    System.out.println("Warning: Invalid Event format -> " + line);
                    return null;
                }
                String desc = taskPart.substring(6, fromIndex).trim();
                String start = taskPart.substring(fromIndex + 6, toIndex).trim();
                String end = taskPart.substring(toIndex + 3, endIndex).trim();
                return new Event(desc, start, end, isCompleted);
            } else if (taskPart.startsWith("[D]")) {
                int byIndex = taskPart.indexOf("(by:");
                int endIndex = taskPart.indexOf(")", byIndex);
                if (byIndex == -1 || endIndex == -1) {
                    System.out.println("Warning: Invalid Deadline format -> " + line);
                    return null;
                }
                String desc = taskPart.substring(6, byIndex).trim();
                String by = taskPart.substring(byIndex + 4, endIndex).trim();
                return new Deadline(desc, by, isCompleted);
            } else {
                System.out.println("Warning: Unknown task type -> " + line);
                return null;
            }
        } catch (Exception e) {
            // catch any unexpected parsing error
            System.out.println("Warning: Could not parse line -> " + line);
            return null;
        }
    }
}
