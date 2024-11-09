package com.ahicode.services;

import com.ahicode.dto.ProjectMemberDto;

import java.util.List;

public interface ProjectDao {
    List<ProjectMemberDto> getProjectsByMember(String nickname);
}
