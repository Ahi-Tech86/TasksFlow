package com.ahicode.storage.repositories;

import com.ahicode.storage.entities.ProjectMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, Long> {
    Optional<ProjectMemberEntity> findById(Long id);
}
