package com.rainstragger.game_backlog_backend.repository;

import com.rainstragger.game_backlog_backend.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Integer> {
    Optional<Library> findByName(String name);

    List<Library> findAllByActive(boolean active);

    @Query("SELECT g FROM Library g WHERE g.active = TRUE AND (LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%')))")
	List<Library> findByTerm(@Param("name") String name);
}
