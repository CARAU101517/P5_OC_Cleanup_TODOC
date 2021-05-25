package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

        @Query("SELECT * FROM Task WHERE id = :id2")
        LiveData<List<Task>> getTask(long id2);

        @Insert
        long insertTask(Task task);

        @Update
        int updateTask(Task task);

        @Query("DELETE FROM Task WHERE id = :id3")
        int deleteTask(long id3);

        @Query("SELECT * FROM Task WHERE projectId = :id1")
        LiveData<List<Task>> getTasksFromProject(long id1);

    }

