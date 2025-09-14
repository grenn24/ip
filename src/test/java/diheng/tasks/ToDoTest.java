package diheng.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest {

    @Test
    void testConstructorAndGetters() {
        ToDo todo = new ToDo("Buy groceries");
        assertEquals("Buy groceries", todo.getDescription());
        assertFalse(todo.isCompleted());
    }

    @Test
    void testConstructorWithCompletion() {
        ToDo todo = new ToDo("Buy groceries", true);
        assertEquals("Buy groceries", todo.getDescription());
        assertTrue(todo.isCompleted());
    }

    @Test
    void testSetCompleted() {
        ToDo todo = new ToDo("Buy groceries");
        todo.setCompleted(true);
        assertTrue(todo.isCompleted());
        todo.setCompleted(false);
        assertFalse(todo.isCompleted());
    }

    @Test
    void testToStringNotCompleted() {
        ToDo todo = new ToDo("Buy groceries");
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    void testToStringCompleted() {
        ToDo todo = new ToDo("Buy groceries", true);
        assertEquals("[T][X] Buy groceries", todo.toString());
    }
}
