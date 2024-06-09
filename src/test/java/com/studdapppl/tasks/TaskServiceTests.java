package com.studdapppl.tasks;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask() {
        Task task = new Task("title", "startDate", "endDate", "isCompleted");
        when(taskRepository.insert(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask("title", "startDate", "endDate", "isCompleted");

        assertEquals(task.getTitle(), createdTask.getTitle());
        assertEquals(task.getStartDate(), createdTask.getStartDate());
        assertEquals(task.getEndDate(), createdTask.getEndDate());
        assertEquals(task.getIsCompleted(), createdTask.getIsCompleted());
    }

    @Test
    void allTasks() {
        Task task = new Task("title", "startDate", "endDate", "isCompleted");
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> tasks = taskService.allTasks();

        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        assertEquals(task.getTitle(), tasks.get(0).getTitle());
    }

    @Test
    void deleteTask() {
        ObjectId id = new ObjectId();
        when(taskRepository.existsById(id)).thenReturn(true);

        boolean isDeleted = taskService.deleteTask(id.toHexString());

        assertTrue(isDeleted);
        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    void updateTask() {
        ObjectId id = new ObjectId();
        Task task = new Task(id, "title", "startDate", "endDate", "isCompleted");
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTask(id.toHexString(), "newTitle", "newStartDate", "newEndDate", "newIsCompleted");

        assertNotNull(updatedTask);
        assertEquals("newTitle", updatedTask.getTitle());
        assertEquals("newStartDate", updatedTask.getStartDate());
        assertEquals("newEndDate", updatedTask.getEndDate());
        assertEquals("newIsCompleted", updatedTask.getIsCompleted());
    }
}
