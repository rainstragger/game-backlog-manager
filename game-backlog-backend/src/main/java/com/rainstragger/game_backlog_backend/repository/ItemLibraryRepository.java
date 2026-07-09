package com.rainstragger.game_backlog_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rainstragger.game_backlog_backend.entities.ItemLibrary;

public interface ItemLibraryRepository extends JpaRepository<ItemLibrary, Integer> {
    Optional<ItemLibrary> findByLibraryId_IdAndGameId_Id(Integer libraryId, Integer gameId);

    List<ItemLibrary> findAllByLibraryId_Id(Integer libraryId);

    @Query("SELECT it FROM ItemLibrary it WHERE it.libraryId.id = :libraryId AND LOWER(it.gameId.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<ItemLibrary> findByLibraryAndName(@Param("libraryId") Integer libraryId, @Param("name") String name);
}
