package com.ahicode.services;

import com.ahicode.dto.ProjectCreationRequest;
import com.ahicode.dto.ProjectDto;
import com.ahicode.dto.ProjectMemberDto;

import java.util.List;

public interface ProjectService {
    ProjectDto saveProject(String token, ProjectCreationRequest projectCreationRequest);
    List<ProjectMemberDto> getProjects(String token);
}
