package com.rainstragger.game_backlog_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainstragger.game_backlog_backend.enums.ProgressEnum;
import com.rainstragger.game_backlog_backend.entities.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    Optional<Progress> findByGame_Id(Integer gameId);

    List<Progress> findAllByStatus(ProgressEnum status);
}
