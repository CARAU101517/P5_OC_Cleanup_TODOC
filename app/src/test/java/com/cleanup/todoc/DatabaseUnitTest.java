package com.cleanup.todoc;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseUnitTest {

    private TaskDataRepository taskDataRepository;
    private ProjectDataRepository projectDataRepository;


    @Test
    public void test_showAllTasks() throws InterruptedException {
        final Task task1 = new Task(1, 1L, "Nettoyer les vitres", new Date().getTime());
        final Task task2 = new Task(2, 2L, "Faire les Poussières", new Date().getTime());
        final Task task3 = new Task(3, 3L, "Passer l'aspirateur", new Date().getTime());
        taskDataRepository.insertTask(task1);
        taskDataRepository.insertTask(task2);
        taskDataRepository.insertTask(task3);
        List<Task> tasks = LiveDataTestUtil.getValue(this.taskDataRepository.getTasks());

        assertEquals(3,tasks.size());

    }


    @Test
    public void test_addATask() throws InterruptedException {
        final Task task1 = new Task(1, 1L, "Nettoyer les vitres", new Date().getTime());
        final Task task2 = new Task(2, 2L, "Faire les Poussières", new Date().getTime());
        final Task task3 = new Task(3, 3L, "Passer l'aspirateur", new Date().getTime());
        taskDataRepository.insertTask(task1);
        taskDataRepository.insertTask(task2);
        taskDataRepository.insertTask(task3);
        List<Task> tasks = LiveDataTestUtil.getValue(this.taskDataRepository.getTasks());
        assertEquals(3, tasks.size());

        Task newTaskToCreate = new Task(4, 1, "Ranger la cuisine", new Date().getTime());
        taskDataRepository.insertTask(newTaskToCreate);
        assertEquals(4, tasks.size());
    }


    @Test
    public void test_updateATask() throws InterruptedException {
        final Task task1 = new Task(1, 1L, "Nettoyer les vitres", new Date().getTime());
        final Task task2 = new Task(2, 2L, "Faire les Poussières", new Date().getTime());
        final Task task3 = new Task(3, 3L, "Passer l'aspirateur", new Date().getTime());
        final Task taskToUpdate = new Task(4, 1L, "Ranger la cuisine", new Date().getTime());
        taskDataRepository.insertTask(task1);
        taskDataRepository.insertTask(task2);
        taskDataRepository.insertTask(task3);
        taskDataRepository.insertTask(taskToUpdate);
        List<Task> tasks = LiveDataTestUtil.getValue(this.taskDataRepository.getTasks());

        taskToUpdate.setName("Vider le Lave-Vaisselles");
        taskDataRepository.updateTask(taskToUpdate);
        assertEquals( "Vider le Lave-Vaisselles", taskToUpdate.getName());
    }


    @Test
    public void test_deleteATask() throws InterruptedException {
        final Task task1 = new Task(1, 1L, "Nettoyer les vitres", new Date().getTime());
        final Task task2 = new Task(2, 2L, "Faire les Poussières", new Date().getTime());
        final Task task3 = new Task(3, 3L, "Passer l'aspirateur", new Date().getTime());
        Task taskToDelete = new Task(4, 1L, "Ranger la cuisine", new Date().getTime());
        taskDataRepository.insertTask(task1);
        taskDataRepository.insertTask(task2);
        taskDataRepository.insertTask(task3);
        taskDataRepository.insertTask(taskToDelete);
        List<Task> tasks = LiveDataTestUtil.getValue(this.taskDataRepository.getTasks());
        assertEquals(4, tasks.size());

        taskDataRepository.deleteTask(taskToDelete);
        assertEquals(3, tasks.size());
    }

    @Test
    public void test_createAndGetAProject() throws InterruptedException {
        Project newProject1 = new Project(4L, "Nouveau projet 1", Color.blue(255));
        projectDataRepository.createProject(newProject1);
        List<Project> projects = (List<Project>) LiveDataTestUtil.getValue(this.projectDataRepository.getProjectById(4L));

        assertEquals(1, projects.size());
    }

    @Test
    public void test_getTasksFromAProject_shouldBeEmpty() throws InterruptedException {
        final Project newProject1 = new Project(4L, "Nouveau projet 1", Color.blue(255));
        projectDataRepository.createProject(newProject1);
        List<Task> tasksForNewProject = LiveDataTestUtil.getValue(this.taskDataRepository.getTasksFromAProject(4L));

        assertTrue(tasksForNewProject.isEmpty());
    }


    @Test
    public void test_getTasksFromAProject() throws InterruptedException {
        final Project newProject1 = new Project(4L, "Nouveau projet 1", Color.blue(255));
        projectDataRepository.createProject(newProject1);
        final Task newTask = new Task(1, 4L, "Nettoyer les vitres", new Date().getTime());
        taskDataRepository.insertTask(newTask);
        List<Task> tasksForNewProject = LiveDataTestUtil.getValue(this.taskDataRepository.getTasksFromAProject(4L));

        assertEquals(1, tasksForNewProject.size());
    }


}
