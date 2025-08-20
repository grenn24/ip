import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class DiHeng {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>();
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
                for (int i = 0; i < inputs.size(); i++) {
                    System.out.printf("%d: %s\n", i + 1 ,inputs.get(i));
                }
            } else {
                inputs.add(curr);
                System.out.printf("Added: %s\n", curr);
            }
        }
        System.out.println("Goodbye!");

    }
}
