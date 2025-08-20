import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DiHeng {
    public static void main(String[] args) {
        List<Task> inputs = new ArrayList<>();
        String greeting = """
                Hello from Di Heng!!!
                It is a pleasure to meet you.
                Please let me know what you need...
                """;
        System.out.println(greeting);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String curr = scanner.nextLine();
            if (curr.equals("bye")) {
                break;
            } else if (curr.equals("list")) {
                System.out.println("Here are the pending tasks:");
                for (int i = 0; i < inputs.size(); i++) {
                    Task task = inputs.get(i);
                    System.out.printf("%d.%s\n", i + 1 , task);
                }
            } else if (curr.startsWith("mark")) {
                int index = Integer.parseInt(curr.split(" ")[1]);
                Task task = inputs.get(index - 1);
                task.setCompleted(true);
                System.out.println("Nice! I've marked this task as done:");
                System.out.printf(" %d.%s\n", index, task);
            } else if (curr.startsWith("unmark")) {
                int index = Integer.parseInt(curr.split(" ")[1]);
                Task task = inputs.get(index - 1);
                task.setCompleted(false);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.printf(" %d.%s\n", index, task);
            }
            else {
                String taskType = curr.split(" ")[0];
                Task currTask;
                switch (taskType) {
                    case "todo":
                        String description = curr.split(" ")[1];
                        currTask = new ToDo(description);
                        break;
                    case "event":
                        String eventDesc = curr.split(" ")[1];
                        String[] parts = curr.split("/from | /to");
                        String start = parts[1];
                        String end = parts[2];
                        currTask = new Event(eventDesc, start, end);
                        break;
                    case "deadline":
                        String deadlineDesc = curr.split(" ")[1];
                        String deadline = curr.split("/by")[1];
                        currTask = new Deadline(deadlineDesc, deadline);
                        break;
                    default:
                        System.out.println("Invalid command");
                        continue;

                }
                inputs.add(currTask);
                System.out.println("Got it. I've added this task:");
                System.out.println(" " + currTask);
                System.out.printf("Now you have %d tasks in the list.\n", inputs.size());
            }
        }
        System.out.println("Goodbye!");

    }
}
