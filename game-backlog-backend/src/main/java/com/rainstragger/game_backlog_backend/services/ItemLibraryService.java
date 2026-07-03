package com.rainstragger.game_backlog_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rainstragger.game_backlog_backend.entities.ItemLibrary;
import com.rainstragger.game_backlog_backend.repository.ItemLibraryRepository;

@Service
public class ItemLibraryService {
    private final ItemLibraryRepository itemLibraryRepository;

    public ItemLibraryService(ItemLibraryRepository itemLibraryRepository) {
        this.itemLibraryRepository = itemLibraryRepository;
    }

    public Optional<ItemLibrary> findById(Integer id) {
        return itemLibraryRepository.findById(id);
    }

    public List<ItemLibrary> findByLibraryAndName(Integer libraryId, String name) {
        return itemLibraryRepository.findByLibraryAndName(libraryId, name);
    }

    public List<ItemLibrary> findAllByLibraryId(Integer libraryId) {
        return itemLibraryRepository.findAllByLibraryId_Id(libraryId);
    }

    public Optional<ItemLibrary> findByLibraryId_IdAndGameId_Id(Integer libraryId, Integer gameId) {
        return itemLibraryRepository.findByLibraryId_IdAndGameId_Id(libraryId, gameId);
    }

    public ItemLibrary save(ItemLibrary itemLibrary) {
        if (itemLibrary.getId() != null) {
            throw new IllegalArgumentException("This Game already exists in this Library");
        }

        boolean alreadyExists = itemLibraryRepository
                .findByLibraryId_IdAndGameId_Id(itemLibrary.getLibraryId().getId(), itemLibrary.getGameId().getId())
                .isPresent();

        if (alreadyExists) {
            throw new IllegalArgumentException("This Game already exists in this Library");
        }

        return itemLibraryRepository.save(itemLibrary);
    }

    public void delete(Integer id) {
        ItemLibrary itemLibrary = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Game not found in this Library"));
        //Hard Delete
        itemLibraryRepository.delete(itemLibrary);
    }
}
