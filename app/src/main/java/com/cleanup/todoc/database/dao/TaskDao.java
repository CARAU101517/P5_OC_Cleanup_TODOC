package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

        //SELECT A TASK FROM ALL TASKS
        @Query("SELECT * FROM Task WHERE id = :id")
        LiveData<List<Task>> getATask(long id);

        // Read all Tasks whathever the project
        @Query("SELECT * FROM TASK")
        LiveData<List<Task>> getTasks();

        //SELECT TASK FROM A PROJECT
        @Query("SELECT * FROM Task WHERE projectId = :projectId")
        LiveData<List<Task>> getTasksFromAProject(long projectId);

        @Insert
        long insertTask(Task task);

        @Update
        int updateTask(Task task);

        @Delete
        int deleteTask(Task task);
        
    }

