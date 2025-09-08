package diheng;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import diheng.enums.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import diheng.exceptions.DiHengException;
import diheng.tasks.TaskList;

class ParserTest {

    private TaskList mockTaskList;
    private Storage mockStorage;
    private Parser parser;

    @BeforeEach
    void setUp() {
        mockTaskList = mock(TaskList.class);
        mockStorage = mock(Storage.class);
        parser = new Parser(mockTaskList, mockStorage);
    }

    @Test
    void testByeCommand() throws DiHengException {
        String message = parser.parse("bye");
        assertEquals("Goodbye!", message, "BYE command should return 'Goodbye!'");
    }

    @Test
    void testListCommand() throws DiHengException {
        parser.parse("list");
        verify(mockTaskList, times(1)).list();
    }

    @Test
    void testMarkCommand() throws DiHengException {
        parser.parse("mark 1 3");
        verify(mockTaskList, times(1)).markTasks(1);
    }

    @Test
    void testUnmarkCommand() throws DiHengException {
        parser.parse("unmark 2 3");
        verify(mockTaskList, times(1)).unmarkTasks(1);
    }

    @Test
    void testDeleteCommand() throws DiHengException {
        parser.parse("delete 3");
        verify(mockTaskList, times(1)).delete(2);
    }

    @Test
    void testClearCommand() throws DiHengException {
        parser.parse("clear");
        verify(mockTaskList, times(1)).clear();
    }

    @Test
    void testTodoCommand() throws DiHengException {
        parser.parse("todo Buy milk");
        verify(mockTaskList, times(1)).add(Command.TODO, "Buy milk");
    }

    @Test
    void testEventCommand() throws DiHengException {
        parser.parse("event Buy milk /from 2pm /to 4pm");
        verify(mockTaskList, times(1)).add(Command.EVENT, "Buy milk /from 2pm /to 4pm");
    }

    @Test
    void testDeadlineCommand() throws DiHengException {
        parser.parse("event Buy milk /by 24/12/2003 16:00");
        verify(mockTaskList, times(1))
                .add(Command.EVENT, "Buy milk /by 24/12/2003 16:00");
    }

    @Test
    void testUnknownCommandThrowsException() {
        DiHengException exception = assertThrows(DiHengException.class, () -> {
            parser.parse("unknownCommand");
        });
        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage()
                        .contains("The supported commands are: list, mark, unmark, todo, event, deadline, bye"),
                "Exception message should indicate valid commands");
        assertTrue(exception.getMessage().contains("unknownCommand"),
                "Exception message should return the unknown command");
    }
}
