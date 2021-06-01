package com.cleanup.todoc.Injections;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDataRepository provideTaskDataRepository(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static ProjectDataRepository provideProjectDataRepository(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static TaskViewModelFactory provideTaskViewModelFactory(Context context) {
        TaskDataRepository taskDataSource = provideTaskDataRepository(context);
        ProjectDataRepository projectDataSource = provideProjectDataRepository(context);
        Executor executor = provideExecutor();

        return new TaskViewModelFactory(projectDataSource, taskDataSource, executor);
    }

}
