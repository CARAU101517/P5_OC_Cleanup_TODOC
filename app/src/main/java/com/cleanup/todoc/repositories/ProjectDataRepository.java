package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao){
        this.projectDao = projectDao;
    }

    public void createProject(Project project){
        projectDao.createProject(project);
    }

    public LiveData<Project> getProjectById(long projectId){
        return this.projectDao.getProjectById(projectId);
    }

}
