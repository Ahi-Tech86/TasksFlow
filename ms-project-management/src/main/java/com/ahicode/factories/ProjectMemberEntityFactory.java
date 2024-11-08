package com.ahicode.factories;

import com.ahicode.storage.entities.ProjectEntity;
import com.ahicode.storage.entities.ProjectMemberEntity;
import org.springframework.stereotype.Component;

import static com.ahicode.storage.enums.ProjectRole.WORKER;
import static com.ahicode.storage.enums.ProjectRole.MANAGER;
import static com.ahicode.storage.enums.ProjectRole.TEAM_LEAD;

@Component
public class ProjectMemberEntityFactory {
    public ProjectMemberEntity makeProjectManagerMemberEntity(ProjectEntity entity, String nickname) {
        return ProjectMemberEntity.builder()
                .project(entity)
                .memberNickname(nickname)
                .projectRole(MANAGER)
                .build();
    }

    public ProjectMemberEntity makeProjectTeamleadMemberEntity(ProjectEntity entity, String nickname) {
        return ProjectMemberEntity.builder()
                .project(entity)
                .memberNickname(nickname)
                .projectRole(TEAM_LEAD)
                .build();
    }

    public ProjectMemberEntity makeProjectWorkerMemberEntity(ProjectEntity entity, String nickname) {
        return ProjectMemberEntity.builder()
                .project(entity)
                .memberNickname(nickname)
                .projectRole(WORKER)
                .build();
    }
}
