package com.ahicode.services;

import com.ahicode.dto.ProjectMemberDto;
import com.ahicode.storage.enums.ProjectRole;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectDaoImpl implements ProjectDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<ProjectMemberDto> getProjectsByMember(String nickname) {
        String sql = "SELECT project.name, project.description, project.create_at," +
                "project_member.user_nick, project_member.role " +
                "FROM project " +
                "JOIN project_member ON project.id = project_member.project_id " +
                "WHERE project_member.user_nick = ?";

        return jdbcTemplate.query(sql, new Object[]{nickname}, rowMapper);
    }

    private RowMapper<ProjectMemberDto> rowMapper = (rs, rowNum) -> {
        ProjectMemberDto dto = new ProjectMemberDto();
        dto.setName(rs.getString("name"));
        dto.setDescription(rs.getString("description"));
        dto.setCreateAt(rs.getObject("create_at", OffsetDateTime.class));
        dto.setUserNick(rs.getString("user_nick"));
        dto.setRole(ProjectRole.valueOf(rs.getString("role")));
        return dto;
    };
}
