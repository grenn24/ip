package diheng.ui;

import java.util.Scanner;

/**
 * Handles user interactions, including reading commands and displaying messages.
 */
public class UI {
    private final Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return the raw user input
     */
    public String readInput() {
        return scanner.nextLine();
    }

    /**
     * Displays a greeting message to the user.
     */
    public void showGreeting() {
        String greeting = """
                Hello from Di Heng!!!
                It is a pleasure to meet you.
                Please let me know what you need...
                """;
        System.out.print(greeting);
    }

    /**
     * Displays an error message to the user.
     *
     * @param e the Exception containing the error message
     */
    public void showError(Exception e) {
        System.out.println(e.getMessage());
    }


    /**
     * Displays a message to the user.
     *
     * @param message the message to display
     * @return whether the program should terminate
     */
    public boolean showMessage(String message) {
        System.out.println(message);
        return message.equals("Goodbye!");
    }

    public void close() {
        scanner.close();
    }
}
