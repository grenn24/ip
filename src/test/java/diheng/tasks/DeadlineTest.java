package diheng.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DeadlineTest {

    @Test
    void testDeadlineToStringNotCompleted() {
        Deadline d = new Deadline("Finish assignment", "24/12/2025 18:00");
        String expected = "[D][ ] Finish assignment (by: 24/12/2025 18:00)";
        assertEquals(expected, d.toString());
    }

    @Test
    void testDeadlineToStringCompleted() {
        Deadline d = new Deadline("Finish assignment", "24/12/2025 18:00", true);
        String expected = "[D][X] Finish assignment (by: 24/12/2025 18:00)";
        assertEquals(expected, d.toString());
    }

    @Test
    void testDeadlineGetDescription() {
        Deadline d = new Deadline("Read book", "01/01/2026 12:00");
        assertEquals("Read book", d.getDescription());
    }

    @Test
    void testDeadlineIsCompleted() {
        Deadline d = new Deadline("Read book", "01/01/2026 12:00", true);
        assertTrue(d.isCompleted());
    }
}
