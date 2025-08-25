package diheng;

import diheng.exceptions.DiHengException;
import diheng.tasks.TaskList;
import diheng.ui.UI;

public class DiHeng {

    private final static String FILENAME = "./data/di-heng.txt";
    private TaskList tasklist;
    private final Storage storage;
    private final Parser parser;
    private final UI ui;

    /**
     * Initializes the DiHeng chatBot, loading tasks from storage and setting up the parser and UI.
     */
    public DiHeng() {
        this.storage = new Storage(FILENAME);
        try {
            this.tasklist = new TaskList(storage.loadTasks());
        } catch (DiHengException e) {
            System.out.println(e.getMessage());
        }
        this.parser = new Parser(this.tasklist);
        this.ui = new UI();
    }

    /**
     * Run the chatBot, reading user input and executing commands in a loop until the user exits.
     */
    public void run() {
        ui.showGreeting();
        while (true) {
            try {
                String input = ui.readInput();
                String message = parser.parse(input);
                boolean isExit = ui.showMessage(message);
                if (isExit) {
                    return;
                }
            } catch (DiHengException | NumberFormatException | IndexOutOfBoundsException e) {
                ui.showError(e);
            }
        }
    }

    public static void main(String[] args) {
        DiHeng chatBot = new DiHeng();
        chatBot.run();
    }
}
