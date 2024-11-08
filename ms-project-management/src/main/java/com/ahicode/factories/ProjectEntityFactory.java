package com.ahicode.factories;

import com.ahicode.dto.ProjectCreationRequest;
import com.ahicode.storage.entities.ProjectEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ProjectEntityFactory {
    public ProjectEntity makeProjectEntity(ProjectCreationRequest projectCreationRequest, Long userId, String projectKey) {
        return ProjectEntity.builder()
                .name(projectCreationRequest.getName())
                .description(projectCreationRequest.getDescription())
                .ownerUserId(userId)
                .projectKey(projectKey)
                .createAt(Instant.now())
                .build();
    }
}
