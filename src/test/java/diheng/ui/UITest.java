package diheng.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private UI ui;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void close() {
        System.setOut(originalOut);
        if (ui != null) {
            ui.close();
        }
    }

    @Test
    void testReadInput() {
        String simulatedInput = "hello world\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ui = new UI();

        String input = ui.readInput();
        assertEquals("hello world", input, "UI should read the correct user input");
    }

    @Test
    void testShowGreeting() {
        ui = new UI();
        ui.showGreeting();

        String output = outContent.toString();
        assertTrue(output.contains("Hello from Di Heng"), "Greeting should contain expected text");
        assertTrue(output.contains("Please let me know what you need"), "Greeting should contain expected text");
    }

    @Test
    void testShowError() {
        ui = new UI();
        Exception e = new Exception("Test error message");
        ui.showError(e);

        String output = outContent.toString().trim();
        assertEquals("Test error message", output, "UI should print the exception message");
    }
}
