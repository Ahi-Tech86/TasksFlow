package com.ahicode.services;

import com.ahicode.dto.MemberDto;

import java.util.List;

public interface ProjectMemberService {
    List<MemberDto> getProjectMembers(Long projectId);
}
