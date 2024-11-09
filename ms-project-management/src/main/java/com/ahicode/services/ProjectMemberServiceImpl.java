package com.ahicode.services;

import com.ahicode.dto.MemberDto;
import com.ahicode.exceptions.AppException;
import com.ahicode.storage.entities.ProjectEntity;
import com.ahicode.storage.repositories.ProjectMemberRepository;
import com.ahicode.storage.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository repository;
    private final ProjectRepository projectRepository;

    @Override
    public List<MemberDto> getProjectMembers(Long projectId) {
        isProjectExistsById(projectId);

        return repository.findProjectMembers(projectId);
    }

    private void isProjectExistsById(Long projectId) {
        Optional<ProjectEntity> optionalProject = projectRepository.findById(projectId);

        if (optionalProject.isEmpty()) {
            log.error("Trying to get information about a non-existent project id {}", projectId);
            throw new AppException("Project doesn't exists", HttpStatus.NOT_FOUND);
        }
    }
}
