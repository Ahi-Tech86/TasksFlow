package com.ahicode.storage.entities;

import com.ahicode.storage.enums.ProjectRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_member")
public class ProjectMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private ProjectEntity project;

    @NotNull
    @Column(name = "user_nick")
    private String memberNickname;

    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ProjectRole projectRole;
}
