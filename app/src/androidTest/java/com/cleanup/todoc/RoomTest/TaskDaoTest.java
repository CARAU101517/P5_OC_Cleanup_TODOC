package com.cleanup.todoc.RoomTest;


import android.graphics.Color;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FOR DATA
    private TodocDatabase database;

    // DATA SET FOR TEST
    private static long PROJECT_ID = 4L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Nouveau Projet 1", Color.blue(255));
    private static long PROJECT_ID_BIS = 5L;
    private static Project PROJECT_DEMO_BIS = new Project(PROJECT_ID_BIS, "Nouveau Projet 2", Color.red(255));

    // TASKS FOR TEST
    private static Task task = new Task(1, PROJECT_ID, "Faire les Poussières", 1);
    private static Task task2 = new Task(2, PROJECT_ID, "Nettoyer les vitres", 1);
    private static Task task3 = new Task(3, PROJECT_ID, "Passer l'aspirateur", 2);
    private static Task task1_bis = new Task(4, PROJECT_ID_BIS, "Ranger la cuisine", 1);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

   @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }


    @Test
    public void TasksPageShouldBeEmpty() throws InterruptedException {
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromAProject(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }

    public void getProjectById() throws InterruptedException {
        //ADD MY DEMO PROJECT
        this.database.projectDao().createProject(PROJECT_DEMO);

        // GET PROJECT AND ASSERT THIS IS PROJECT DEMO
        Project project = LiveDataTestUtil.getValue(this.database.projectDao()
                .getProjectById(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) &&
                project.getColor() == PROJECT_DEMO.getColor());
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        // BEFORE : Add project Demo & add demo tasks
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(task);
        this.database.taskDao().insertTask(task2);
        this.database.taskDao().insertTask(task3);

        // TEST get tasks List = 3
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromAProject(PROJECT_ID));
        assertTrue(taskList.size() == 3);
    }

    @Test
    public void insertAndUpdateTask() throws InterruptedException {
        // BEFORE : GET demo project & demo task. Next, update task added & re-save it
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(task);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromAProject(PROJECT_ID)).get(0);
        taskAdded.setName("Faire la vaisselle");
        this.database.taskDao().updateTask(taskAdded);

        //TEST
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromAProject(PROJECT_ID));
        assertTrue(!taskList.get(0).getName().equals(task.getName()) && taskList.get(0).getName().equals("Faire la vaisselle"));
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // BEFORE : GET demo project & demo task. Next, get the task added & delete it.
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(task2);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromAProject(PROJECT_ID)).get(0);
        this.database.taskDao().deleteTask(taskAdded);

        //TEST
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromAProject(PROJECT_ID));
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void insertAndGetAllTasksWatheverProject() throws InterruptedException {
        // ADD PROJECTS IN DATABASE
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.projectDao().createProject(PROJECT_DEMO_BIS);

        // ADD TASKS IN PROJECTS
        this.database.taskDao().insertTask(task);
        this.database.taskDao().insertTask(task1_bis);

        // TEST OUR getTasks() METHOD
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());

        assertTrue(tasks.size() == 2 && task.getProjectId() == PROJECT_ID &&
                task1_bis.getProjectId() == PROJECT_ID_BIS);
    }

}
