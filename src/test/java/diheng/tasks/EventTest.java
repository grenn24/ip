package diheng.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {

    @Test
    void testEventToStringNotCompleted() {
        Event e = new Event("Team meeting", "10:00", "11:00");
        String expected = "[E][ ] Team meeting (from: 10:00 to: 11:00)";
        assertEquals(expected, e.toString());
    }

    @Test
    void testEventToStringCompleted() {
        Event e = new Event("Team meeting", "10:00", "11:00", true);
        String expected = "[E][X] Team meeting (from: 10:00 to: 11:00)";
        assertEquals(expected, e.toString());
    }

    @Test
    void testEventGetDescription() {
        Event e = new Event("Project demo", "14:00", "15:00");
        assertEquals("Project demo", e.getDescription());
    }

    @Test
    void testEventIsCompleted() {
        Event e = new Event("Project demo", "14:00", "15:00", true);
        assertTrue(e.isCompleted());
    }
}
