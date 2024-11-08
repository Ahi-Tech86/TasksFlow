package com.ahicode.storage.repositories;

import com.ahicode.storage.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findById(Long id);
    Optional<ProjectEntity> findByProjectKey(String projectKey);
}
