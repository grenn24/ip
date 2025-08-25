package diheng;

import java.util.Scanner;

public class UI {

    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showGreeting() {
        String greeting = """
                Hello from Di Heng!!!
                It is a pleasure to meet you.
                Please let me know what you need...
                """;
        System.out.print(greeting);
    }

    public void showError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }

    public void close() {
        scanner.close();
    }
}
