package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final ProjectDataRepository projectDataRepository;
    private final TaskDataRepository taskDataRepository;
    private final Executor executor;

    public TaskViewModel(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor){
        this.projectDataRepository = projectDataRepository;
        this.taskDataRepository = taskDataRepository;
        this.executor = executor;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final Project[] allProjects = Project.getAllProjects();
                for (Project project : allProjects) {
                    projectDataRepository.createProject(project);
                }
            }
        });
    }

    public LiveData<List<Task>> allTasks() {
        return taskDataRepository.getTasks();
    }

    public LiveData<List<Task>> aTask(long id) {
        return taskDataRepository.getATask(id);
    }

    public LiveData<List<Task>> allTasksFromAProject(long projectId) {
        return  taskDataRepository.getTasksFromAProject(projectId);
    }

    public void createATask(Task task) {
        executor.execute(() -> taskDataRepository.insertTask(task));
    }

    public void updateATask(Task task) {
        executor.execute(() -> taskDataRepository.updateTask(task));
    }

    public void deleteATask(Task task) {
        executor.execute(() -> taskDataRepository.deleteTask(task));
    }


}
