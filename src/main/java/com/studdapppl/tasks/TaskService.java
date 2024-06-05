package com.studdapppl.tasks;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(String title, String startDate, String endDate, String isCompleted) {
        Task task = taskRepository.insert(new Task(title, startDate, endDate, isCompleted));
        return task;
    }

    public List<Task> allTasks() {
        return taskRepository.findAll();
    }

    public boolean deleteTask(String id) {
        if (taskRepository.existsById(new ObjectId(id))) {
            taskRepository.deleteById(new ObjectId(id));
            return true;
        } else {
            return false;
        }
    }

    public Task updateTask(String id, String title, String startDate, String endDate, String isCompleted) {
        Task task = taskRepository.findById(new ObjectId(id)).orElse(null);
        if (task != null) {
            task.setTitle(title);
            task.setStartDate(startDate);
            task.setEndDate(endDate);
            task.setIsCompleted(isCompleted);
            taskRepository.save(task);
            return task;
        } else {
            return null;
        }
    }
}