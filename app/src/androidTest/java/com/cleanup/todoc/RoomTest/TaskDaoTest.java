package com.cleanup.todoc.RoomTest;

import android.content.Context;

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
    private static long PROJECT_ID = 1L;
    private static Task NEW_TASK = new Task(1, PROJECT_ID, "Nettoyer les vitres", 1);


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
    public void GetProject() throws InterruptedException {
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProjectById(PROJECT_ID));
        assertTrue(project.getName().equals(project.getName()) && project.getId() == PROJECT_ID);
    }

    @Test
    public void getItemsWhenNoTaskInserted() throws InterruptedException {
        // TEST
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        // BEFORE : GET demo project & demo task

        this.database.projectDao().getProjectById(PROJECT_ID);
        this.database.taskDao().insertTask(NEW_TASK);

        // TEST
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(taskList.size() == 1);
    }

    @Test
    public void insertAndUpdateTask() throws InterruptedException {
        // BEFORE : GET demo project & demo task. Next, update task added & re-save it
        this.database.projectDao().getProjectById(PROJECT_ID);
        this.database.taskDao().insertTask(NEW_TASK);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID)).get(0);
        this.database.taskDao().updateTask(taskAdded);

        //TEST
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(taskList.size() == 1);
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // BEFORE : GET demo project & demo task. Next, get the task added & delete it.
        this.database.projectDao().getProjectById(PROJECT_ID);
        this.database.taskDao().insertTask(NEW_TASK);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID)).get(0);
        this.database.taskDao().deleteTask(taskAdded);

        //TEST
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(taskList.isEmpty());
    }


}
