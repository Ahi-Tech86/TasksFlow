package com.ahicode.dto;

import com.ahicode.storage.enums.ProjectRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberDto {
    @JsonProperty("project_name")
    private String name;
    @JsonProperty("project_description")
    private String description;
    @JsonProperty("create_at")
    private OffsetDateTime createAt;
    @JsonProperty("nickname")
    private String userNick;
    private ProjectRole role;
}
