package com.ahicode.storage.repositories;

import com.ahicode.storage.entities.RefreshTokenEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    @NotNull Optional<RefreshTokenEntity> findById(@NotNull Long id);
}
