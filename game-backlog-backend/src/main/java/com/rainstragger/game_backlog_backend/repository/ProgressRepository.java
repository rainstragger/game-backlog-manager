package com.rainstragger.game_backlog_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rainstragger.game_backlog_backend.entities.Progress;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    Optional<Progress> findByItemLibrary_Id(Integer itemLibraryId);

    List<Progress> findAllByItemLibrary_LibraryId_Id(Integer libraryId);

    List<Progress> findAllByItemLibrary_LibraryId_IdAndStatusIgnoreCase(Integer libraryId, String status);
}
