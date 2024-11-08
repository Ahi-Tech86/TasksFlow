package com.ahicode.services;

import com.ahicode.dto.ProjectCreationRequest;
import com.ahicode.dto.ProjectDto;
import com.ahicode.dto.ProjectMemberDto;
import com.ahicode.exceptions.AppException;
import com.ahicode.factories.ProjectDtoFactory;
import com.ahicode.factories.ProjectEntityFactory;
import com.ahicode.factories.ProjectMemberEntityFactory;
import com.ahicode.storage.entities.ProjectEntity;
import com.ahicode.storage.entities.ProjectMemberEntity;
import com.ahicode.storage.repositories.ProjectMemberRepository;
import com.ahicode.storage.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final JwtServiceImpl jwtService;
    private final ProjectDtoFactory dtoFactory;
    private final ProjectEntityFactory entityFactory;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository membersRepository;
    private final ProjectMemberEntityFactory memberEntityFactory;

    @Override
    @Transactional
    public ProjectDto saveProject(String token, ProjectCreationRequest projectCreationRequest) {
        String projectName = projectCreationRequest.getName();
        Long ownerId = jwtService.extractUserIdFromAccessToken(token);
        String nickname = jwtService.extractEmailFromAccessToken(token);

        String projectKey = makeProjectKey(projectName, ownerId);
        isProjectUniqueness(projectKey);

        ProjectEntity project = entityFactory.makeProjectEntity(projectCreationRequest, ownerId, projectKey);
        ProjectEntity savedProject = projectRepository.save(project);
        log.info("{} user created new project: {}", nickname, projectName);

        ProjectMemberEntity projectMember = memberEntityFactory.makeProjectManagerMemberEntity(savedProject, nickname);
        membersRepository.save(projectMember);
        log.info("User {} joined the project {}", nickname, projectName);

        return dtoFactory.makeProjectDto(savedProject);
    }

    @Override
    public List<ProjectMemberDto> getProjects(String token) {
        String nickname = jwtService.extractEmailFromAccessToken(token);

        return List.of();
    }

    private void isProjectUniqueness(String projectKey) {
        Optional<ProjectEntity> optionalProject = projectRepository.findByProjectKey(projectKey);

        if (optionalProject.isPresent()) {
            log.error("Attempt to create a project with a non-unique projectKey: {}", projectKey);
            throw new AppException("The name of your project is not unique, please change its name", HttpStatus.BAD_REQUEST);
        }
    }

    private String makeProjectKey(String projectName, Long ownerId) {
        String[] words = projectName.trim().split("\\s+");

        StringBuilder camelCaseString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                camelCaseString.append(capitalizedWord);
            }
        }

        String name = camelCaseString.toString();

        return name + ":" + ownerId;
    }
}
