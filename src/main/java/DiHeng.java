import java.util.Scanner;

public class DiHeng {
    public static void main(String[] args) {
        String greeting = """
                Hello from Di Heng!!!
                It is a pleasure to meet you.
                Please let me know what you need...
                """;
        System.out.println(greeting);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else {
                System.out.println(input);
            }
        }
        System.out.println("Goodbye!");

    }
}
