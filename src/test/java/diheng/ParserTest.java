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
    private Parser parser;

    @BeforeEach
    void setUp() {
        mockTaskList = mock(TaskList.class);
        parser = new Parser(mockTaskList);
    }

    @Test
    void testByeCommand() throws DiHengException {
        boolean result = parser.parse("bye");
        assertTrue(result, "BYE command should return true to terminate the program");
    }

    @Test
    void testListCommand() throws DiHengException {
        parser.parse("list");
        verify(mockTaskList, times(1)).listTasks();
    }

    @Test
    void testMarkCommand() throws DiHengException {
        parser.parse("mark 1");
        verify(mockTaskList, times(1)).markTask(0);
    }

    @Test
    void testUnmarkCommand() throws DiHengException {
        parser.parse("unmark 2");
        verify(mockTaskList, times(1)).unmarkTask(1);
    }

    @Test
    void testDeleteCommand() throws DiHengException {
        parser.parse("delete 3");
        verify(mockTaskList, times(1)).deleteTask(2);
    }

    @Test
    void testClearCommand() throws DiHengException {
        parser.parse("clear");
        verify(mockTaskList, times(1)).clearTasks();
    }

    @Test
    void testTodoCommand() throws DiHengException {
        parser.parse("todo Buy milk");
        verify(mockTaskList, times(1)).createTask(Command.TODO, "Buy milk");
    }

    @Test
    void testEventCommand() throws DiHengException {
        parser.parse("event Buy milk /from 2pm /to 4pm");
        verify(mockTaskList, times(1)).createTask(Command.EVENT, "Buy milk /from 2pm /to 4pm");
    }

    @Test
    void testDeadlineCommand() throws DiHengException {
        parser.parse("event Buy milk /by 24/12/2003 16:00");
        verify(mockTaskList, times(1))
                .createTask(Command.EVENT, "Buy milk /by 24/12/2003 16:00");
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
