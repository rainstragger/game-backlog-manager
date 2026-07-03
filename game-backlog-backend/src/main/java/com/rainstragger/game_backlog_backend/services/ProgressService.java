package com.rainstragger.game_backlog_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rainstragger.game_backlog_backend.entities.Progress;
import com.rainstragger.game_backlog_backend.repository.ItemLibraryRepository;
import com.rainstragger.game_backlog_backend.repository.ProgressRepository;

@Service
public class ProgressService {
    private final ProgressRepository progressRepository;
    private final ItemLibraryRepository itemLibraryRepository;

    public ProgressService(ProgressRepository progressRepository, ItemLibraryRepository itemLibraryRepository) {
        this.progressRepository = progressRepository;
        this.itemLibraryRepository = itemLibraryRepository;
    }

    public List<Progress> findAllByLibraryId(Integer libraryId) {
        return progressRepository.findAllByItemLibrary_LibraryId_Id(libraryId);
    }

    public List<Progress> findAllByLibraryIdAndStatus(Integer libraryId, String status) {
        return progressRepository.findAllByItemLibrary_LibraryId_IdAndStatusIgnoreCase(libraryId, status);
    }

    public Optional<Progress> findById(Integer id) {
        return progressRepository.findById(id);
    }

    public Optional<Progress> findByItemLibraryId(Integer itemLibraryId) {
        return progressRepository.findByItemLibrary_Id(itemLibraryId);
    }

    public Progress create(Progress progress) {
        if (progress.getId() != null) {
            throw new IllegalArgumentException("Progress already exists");
        }

        Integer itemLibraryId = progress.getItemLibrary().getId();
        boolean itemLibraryExists = itemLibraryRepository.findById(itemLibraryId).isPresent();
        boolean progressAlreadyExists = progressRepository.findByItemLibrary_Id(itemLibraryId).isPresent();

        if (!itemLibraryExists) {
            throw new IllegalArgumentException("Item Library not found");
        }

        if (progressAlreadyExists) {
            throw new IllegalArgumentException("Progress already exists for this Item Library");
        }

        return progressRepository.save(progress);
    }

    public Progress update(Integer id, Progress progress) {
        Progress existingProgress = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Progress not found"));

        existingProgress.setStatus(progress.getStatus());
        existingProgress.setStartedAt(progress.getStartedAt());
        existingProgress.setCompletedAt(progress.getCompletedAt());

        return progressRepository.save(existingProgress);
    }

    public void delete(Integer id) {
        Progress progress = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Progress not found"));

        progressRepository.delete(progress);
    }
}
