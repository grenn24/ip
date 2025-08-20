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
                System.out.println("I have marked this task as completed");
                System.out.printf("%d.%s\n", index, task);
            } else if (curr.startsWith("unmark")) {
                int index = Integer.parseInt(curr.split(" ")[1]);
                Task task = inputs.get(index - 1);
                task.setCompleted(false);
                System.out.println("I have marked this task as uncompleted");
                System.out.printf("%d.%s\n", index, task);
            }
            else {
                Task currTask = new Task(curr);
                inputs.add(currTask);
                System.out.printf("Added: %s\n", curr);
            }
        }
        System.out.println("Goodbye!");

    }
}
