package com.ahicode.storage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 75)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(columnDefinition = "text")
    private String description;

    @NotNull
    @Column(name = "owner_id")
    private Long ownerUserId;

    @NotNull
    @Column(name = "project_key", unique = true)
    private String projectKey;

    @NotNull
    @Column(name = "create_at")
    private Instant createAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMemberEntity> members;
}
