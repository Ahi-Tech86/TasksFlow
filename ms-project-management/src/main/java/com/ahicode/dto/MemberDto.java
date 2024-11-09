package com.ahicode.dto;

import com.ahicode.storage.enums.ProjectRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class MemberDto {
    private String nickname;
    private ProjectRole role;
}
