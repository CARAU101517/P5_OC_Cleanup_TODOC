package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getATask(long id) {
        return this.taskDao.getATask(id);
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }

    public LiveData<List<Task>> getTasksFromAProject(long projectId) {
        return  this.taskDao.getTasksFromAProject(projectId);
    }

    public void insertTask(Task task) {
        taskDao.insertTask(task);
    }

    public void updateTask (Task task) {
        taskDao.updateTask(task);
    }

    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }

}
