package com.cleanup.todoc.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

        // --- SINGLETON ---
        private static volatile TodocDatabase INSTANCE;


    // --- DAOs ---
        public abstract ProjectDao projectDao();
        public abstract TaskDao taskDao();


        // --- INSTANCE ---
        public static TodocDatabase getInstance(Context context) {
            if (INSTANCE == null) {
                synchronized (TodocDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                TodocDatabase.class, "MyTodocDatabase.db")
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

    }


