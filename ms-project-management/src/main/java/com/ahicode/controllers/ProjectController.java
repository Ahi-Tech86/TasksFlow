package com.ahicode.controllers;

import com.ahicode.dto.MemberDto;
import com.ahicode.dto.ProjectCreationRequest;
import com.ahicode.dto.ProjectDto;
import com.ahicode.dto.ProjectMemberDto;
import com.ahicode.services.ProjectMemberServiceImpl;
import com.ahicode.services.ProjectServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectServiceImpl service;
    private final ProjectMemberServiceImpl memberService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(
            @CookieValue(value = "accessToken") String accessToken,
            @Valid @RequestBody ProjectCreationRequest projectCreationRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveProject(accessToken, projectCreationRequest));
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectMemberDto>> getAllProjects(
            @CookieValue(value = "accessToken") String accessToken
    ) {
        return ResponseEntity.ok(service.getProjects(accessToken));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberDto>> getProjectMembers(
            @PathVariable(name = "id") Long projectId
    ) {
        return ResponseEntity.ok(memberService.getProjectMembers(projectId));
    }
}
