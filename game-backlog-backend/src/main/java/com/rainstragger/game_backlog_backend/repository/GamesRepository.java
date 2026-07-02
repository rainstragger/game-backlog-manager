package com.rainstragger.game_backlog_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rainstragger.game_backlog_backend.entities.Games;

public interface GamesRepository extends JpaRepository<Games, Integer> {

    Optional<Games> findByName(String name);

    List<Games> findAllByActive(boolean active);

    @Query("SELECT g FROM Games g WHERE g.active = TRUE AND (LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%')))")
	List<Games> findByTerm(@Param("name") String name);

    
}
