package diheng.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskConstructorAndGetters() {
        Task t = new Task("Finish homework");
        assertEquals("Finish homework", t.getDescription());
        assertFalse(t.isCompleted());
    }

    @Test
    void testTaskConstructorWithCompletion() {
        Task t = new Task("Finish homework", true);
        assertEquals("Finish homework", t.getDescription());
        assertTrue(t.isCompleted());
    }

    @Test
    void testSetCompleted() {
        Task t = new Task("Finish homework");
        t.setCompleted(true);
        assertTrue(t.isCompleted());
        t.setCompleted(false);
        assertFalse(t.isCompleted());
    }

    @Test
    void testToStringNotCompleted() {
        Task t = new Task("Finish homework");
        assertEquals("[ ] Finish homework", t.toString());
    }

    @Test
    void testToStringCompleted() {
        Task t = new Task("Finish homework", true);
        assertEquals("[X] Finish homework", t.toString());
    }
}
