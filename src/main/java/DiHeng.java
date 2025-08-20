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
                    System.out.printf("%d[%s]: %s\n", i + 1 , task.isCompleted() ? "X" : " "  , task.getDescription());
                }
            } else {
                Task currTask = new Task(curr);
                inputs.add(currTask);
                System.out.printf("Added: %s\n", curr);
            }
        }
        System.out.println("Goodbye!");

    }
}
