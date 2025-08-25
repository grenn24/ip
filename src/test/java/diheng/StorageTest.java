package diheng;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import diheng.exceptions.DiHengException;
import diheng.tasks.Deadline;
import diheng.tasks.Event;
import diheng.tasks.Task;
import diheng.tasks.ToDo;

public class StorageTest {

    private static final String FILE_PATH = "./data/temp-test.txt";
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(FILE_PATH);
    }

    @AfterEach
    void close() throws Exception {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            Files.delete(file.toPath());
        }
    }

    @Test
    void testSaveAndLoad() throws DiHengException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new ToDo("todo1", false));
        tasksToSave.add(new ToDo("todo2", true));
        tasksToSave.add(new Deadline("submit report", "24/12/2025 18:00", false));
        tasksToSave.add(new Event("meeting", "10:00", "11:00", true));

        storage.saveTasks(tasksToSave);

        List<Task> loadedTasks = storage.loadTasks();

        assertEquals(tasksToSave.size(), loadedTasks.size(), "Number of loaded tasks should match");

        for (int i = 0; i < tasksToSave.size(); i++) {
            Task original = tasksToSave.get(i);
            Task loaded = loadedTasks.get(i);
            assertEquals(original.toString(), loaded.toString(), "Task string representation should match");
        }
    }

    @Test
    void testLoadEmptyFile() throws Exception {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            Files.delete(file.toPath());
        }

        List<Task> loadedTasks = storage.loadTasks();
        assertTrue(loadedTasks.isEmpty(),
                "Loading from a non-existent file should return an empty list of tasks");
    }

    @Test
    void testSave() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("todo1", false));

        storage.saveTasks(tasks);

        File file = new File(FILE_PATH);
        assertTrue(file.exists(), "Saving a file should create the file in the disk");
        assertTrue(file.length() > 0, "Saved file should not be empty");
    }
}