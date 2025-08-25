package diheng;

import diheng.exceptions.DiHengException;
import diheng.tasks.TaskList;
import diheng.ui.UI;

public class DiHeng {

    private final static String FILENAME = "./data/di-heng.txt";
    private TaskList tasklist;
    private Storage storage;
    private final Parser parser;
    private final UI ui;

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

    public void run() {
        ui.showGreeting();
        while (true) {
            try {
                String input = ui.readCommand();
                boolean isExit = parser.parse(input);
                if (isExit) {
                    return;
                }
            } catch (DiHengException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                ui.showError("Invalid task index provided. Please check your command and try again.");
            }
        }
    }

    public static void main(String[] args) {
        DiHeng chatbot = new DiHeng();
        chatbot.run();
    }
}
