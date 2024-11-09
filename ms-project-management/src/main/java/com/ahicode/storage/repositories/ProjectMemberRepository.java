package com.ahicode.storage.repositories;

import com.ahicode.dto.MemberDto;
import com.ahicode.storage.entities.ProjectMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, Long> {
    Optional<ProjectMemberEntity> findById(Long id);

    @Query("SELECT new com.ahicode.dto.MemberDto(pm.memberNickname, pm.projectRole) FROM ProjectMemberEntity pm " +
           "WHERE pm.project.id = :projectId"
    )
    List<MemberDto> findProjectMembers(@Param("projectId") Long projectId);
}
