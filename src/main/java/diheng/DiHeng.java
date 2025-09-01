package diheng;

import diheng.exceptions.DiHengException;
import diheng.tasks.TaskList;
import diheng.ui.UI;

/**
 * The main class of the DiHeng chatBot.
 */
public class DiHeng {
    /**
     * The file name of the storage file.
     */
    private final static String FILENAME = "./data/di-heng.txt";
    /**
     * The task list to be used by the chatBot.
     */
    private TaskList tasklist;
    /**
     * The storage to be used by the chatBot.
     */
    private final Storage storage;
    /**
     * The parser to be used by the chatBot.
     */
    private final Parser parser;
    /**
     * The UI to be used by the chatBot.
     */
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

    /**
     * Generates a response for the user's chat message.
     *
     * @param input The user's chat message.
     * @return The chatBot's response.
     */
    public String getInputResponse(String input) {
        try {
            return parser.parse(input);
        } catch (DiHengException | NumberFormatException | IndexOutOfBoundsException e) {
            return e.getMessage();
        }
    }
}
