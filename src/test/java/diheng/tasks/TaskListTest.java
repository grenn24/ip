package diheng.tasks;

import diheng.enums.Command;
import diheng.exceptions.DiHengException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testAddToDo() throws DiHengException {
        String response = taskList.add(Command.TODO, "Buy milk");
        assertTrue(response.contains("Buy milk"));
        assertEquals(1, taskList.list().lines().count());
    }

    @Test
    void testAddDeadline() throws DiHengException {
        String response = taskList.add(Command.DEADLINE, "Submit report /by 31/12/2025 23:59");
        assertTrue(response.contains("Submit report"));
        assertTrue(response.contains("31/12/2025 23:59"));
    }

    @Test
    void testAddEvent() throws DiHengException {
        String response = taskList.add(Command.EVENT, "Meeting /from 01/01/2026 10:00 /to 01/01/2026 12:00");
        assertTrue(response.contains("Meeting"));
        assertTrue(response.contains("01/01/2026 10:00"));
        assertTrue(response.contains("01/01/2026 12:00"));
    }

    @Test
    void testMarkUnmarkTasks() throws DiHengException {
        taskList.add(Command.TODO, "Test task");
        String markResponse = taskList.markTasks(0);
        assertTrue(markResponse.contains("Task completed"));
        String unmarkResponse = taskList.unmarkTasks(0);
        assertTrue(unmarkResponse.contains("marked as not done"));
    }

    @Test
    void testDeleteTask() throws DiHengException {
        taskList.add(Command.TODO, "Task to delete");
        String deleteResponse = taskList.delete(0);
        assertTrue(deleteResponse.contains("Removed this task"));
        assertTrue(taskList.list().contains("no tasks"));
    }

    @Test
    void testClearTasks() throws DiHengException {
        taskList.add(Command.TODO, "Task 1");
        taskList.add(Command.TODO, "Task 2");
        String clearResponse = taskList.clear();
        assertTrue(clearResponse.contains("All tasks cleared"));
        assertTrue(taskList.list().contains("no tasks"));
    }

    @Test
    void testFindTasks() throws DiHengException {
        taskList.add(Command.TODO, "Read book");
        taskList.add(Command.TODO, "Write code");
        String findResponse = taskList.find("book");
        assertTrue(findResponse.contains("Read book"));
        assertFalse(findResponse.contains("Write code"));
    }
}
