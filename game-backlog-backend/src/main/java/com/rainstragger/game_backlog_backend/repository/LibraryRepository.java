package com.rainstragger.game_backlog_backend.repository;

import com.rainstragger.game_backlog_backend.entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

    Optional<Library> findById(Integer id);

    Optional<Library> findByName(String name);

    List<Library> findAllByActiveTrue();

    @Query("SELECT l FROM Library l WHERE l.active = TRUE AND (LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%')))")
	List<Library> findByTerm(@Param("name") String name);
}
