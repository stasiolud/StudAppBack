package com.studdapppl.tasks;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask() {
        Task task = new Task("title", "startDate", "endDate", "isCompleted");
        when(taskService.createTask(anyString(), anyString(), anyString(), anyString())).thenReturn(task);

        ResponseEntity<Task> response = taskController.createTask(Map.of("title", "title", "startDate", "startDate", "endDate", "endDate", "isCompleted", "isCompleted"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void getAllTasks() {
        Task task = new Task("title", "startDate", "endDate", "isCompleted");
        when(taskService.allTasks()).thenReturn(List.of(task));

        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(task, response.getBody().get(0));
    }

    @Test
    void deleteTask() {
        when(taskService.deleteTask(anyString())).thenReturn(true);

        ResponseEntity<Void> response = taskController.deleteTask("someId");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask("someId");
    }

    @Test
    void updateTask() {
        Task task = new Task(new ObjectId(), "title", "startDate", "endDate", "isCompleted");
        when(taskService.updateTask(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(task);

        ResponseEntity<Task> response = taskController.updateTask("someId", Map.of("title", "newTitle", "startDate", "newStartDate", "endDate", "newEndDate", "isCompleted", "newIsCompleted"));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }
}
