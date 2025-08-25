
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiHeng {

    private List<Task> tasks = new ArrayList<>();
    private final static String FILENAME = "./data/di-heng.txt";

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
        saveTasksToDisk(tasks);
    }

    public void unmarkTask(int index) throws DiHengException {
        Task task = this.tasks.get(index);
        task.setCompleted(false);

        System.out.println("OK, I've marked this task as not done yet:");
        System.out.printf(" %d.%s\n", index + 1, task);
        saveTasksToDisk(tasks);
    }

    public void deleteTask(int index) throws DiHengException {
        Task task = this.tasks.get(index);
        this.tasks.remove(index);

        System.out.println("Noted. I've removed this task:");
        System.out.printf(" %d.%s\n", index + 1, task);
        System.out.printf("Now you have %d tasks in the list.\n", this.tasks.size());
        saveTasksToDisk(tasks);
    }

    public void clearTasks() throws DiHengException {
        tasks.clear();
        System.out.println("All tasks have been cleared.");
        saveTasksToDisk(tasks);
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
        saveTasksToDisk(tasks);
        return currTask;
    }

    public void saveTasksToDisk(List<Task> tasks) throws DiHengException {
        File file = new File(FILENAME);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
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

    public void loadTasksFromDisk() throws DiHengException {
        File file = new File(FILENAME);
        if (!file.exists()) {
            // No file yet; nothing to load
            tasks = new ArrayList<>();
            return;
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

        this.tasks = loadedTasks;
    }

    private Task parseTaskFromString(String line) {
        try {
            int dotIndex = line.indexOf(".");
            if (dotIndex == -1) {
                System.out.println("Warning: Invalid task format -> " + line);
                return null;
            }

            String taskPart = line.substring(dotIndex + 1).trim();

            if (taskPart.startsWith("[T]")) {
                // ToDo
                String desc = taskPart.substring(6).trim();
                return new ToDo(desc);
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
                return new Event(desc, start, end);
            } else if (taskPart.startsWith("[D]")) {
                int byIndex = taskPart.indexOf("(by:");
                int endIndex = taskPart.indexOf(")", byIndex);
                if (byIndex == -1 || endIndex == -1) {
                    System.out.println("Warning: Invalid Deadline format -> " + line);
                    return null;
                }
                String desc = taskPart.substring(6, byIndex).trim();
                String by = taskPart.substring(byIndex + 4, endIndex).trim();
                return new Deadline(desc, by);
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

    public static void main(String[] args) {
        DiHeng chatbot = new DiHeng();
        String greeting = """
                Hello from Di Heng!!!
                It is a pleasure to meet you.
                Please let me know what you need...
                """;
        System.out.print(greeting);
        Scanner scanner = new Scanner(System.in);

        try {
            chatbot.loadTasksFromDisk();
        } catch (DiHengException e) {
            System.out.println(e.getMessage());
        }

        while (scanner.hasNextLine()) {
            try {
                String curr = scanner.nextLine();
                String[] tokens = curr.split(" ", 2);
                Command command;

                try {
                    command = Command.valueOf(tokens[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    command = Command.UNKNOWN;
                }
                String commandArgs = tokens.length > 1 ? tokens[1] : "";

                switch (command) {
                    case BYE -> {
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;
                    }
                    case LIST -> {
                        chatbot.listTasks();
                    }
                    case MARK -> {
                        if (commandArgs.isEmpty()) {
                            throw new DiHengException("Missing task index",
                                    "Please provide the index of the task to mark.");
                        }
                        int index = Integer.parseInt(commandArgs);
                        chatbot.markTask(index - 1);
                    }
                    case UNMARK -> {
                        if (commandArgs.isEmpty()) {
                            throw new DiHengException("Missing task index",
                                    "Please provide the index of the task to unmark.");
                        }
                        int index = Integer.parseInt(commandArgs);
                        chatbot.unmarkTask(index - 1);
                    }
                    case DELETE -> {
                        if (commandArgs.isEmpty()) {
                            throw new DiHengException("Missing task index",
                                    "Please provide the index of the task to delete.");
                        }
                        int index = Integer.parseInt(commandArgs);
                        chatbot.deleteTask(index - 1);
                    }
                    case CLEAR -> {
                        chatbot.clearTasks();
                    }
                    case TODO, EVENT, DEADLINE -> {
                        if (commandArgs.isEmpty()) {
                            throw new DiHengException("Missing task description",
                                    "Please provide the description of the task.");
                        }
                        chatbot.createTask(command, commandArgs);
                    }
                    default ->
                        throw new DiHengException(
                                "Unknown command",
                                "The supported commands are: list, mark, unmark, todo, event, deadline, bye"
                        );
                }

            } catch (DiHengException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Invalid task index provided. Please check your command and try again.");
            }
        }

    }
}
