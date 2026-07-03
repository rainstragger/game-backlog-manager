package com.rainstragger.game_backlog_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rainstragger.game_backlog_backend.dto.ItemLibraryDTO;
import com.rainstragger.game_backlog_backend.entities.ItemLibrary;
import com.rainstragger.game_backlog_backend.mappers.ItemLibraryMapper;
import com.rainstragger.game_backlog_backend.services.ItemLibraryService;

@RestController
@RequestMapping("/item-library")
public class ItemLibraryController {
    private final ItemLibraryService itemLibraryService;
    private final ItemLibraryMapper itemLibraryMapper;

    public ItemLibraryController(ItemLibraryService itemLibraryService, ItemLibraryMapper itemLibraryMapper) {
        this.itemLibraryService = itemLibraryService;
        this.itemLibraryMapper = itemLibraryMapper;
    }

    @GetMapping("/library/{libraryId}")
    public ResponseEntity<List<ItemLibraryDTO>> findAllByLibraryId(@PathVariable Integer libraryId) {
        List<ItemLibraryDTO> itemLibraries = itemLibraryService.findAllByLibraryId(libraryId).stream()
                .map(itemLibraryMapper::toDTO)
                .toList();
        return ResponseEntity.ok(itemLibraries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemLibraryDTO> findById(@PathVariable Integer id) {
        Optional<ItemLibraryDTO> itemLibrary = itemLibraryService.findById(id)
                .map(itemLibraryMapper::toDTO);
        return itemLibrary.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/library/{libraryId}/game/{gameId}")
    public ResponseEntity<ItemLibraryDTO> findByLibraryIdAndGameId(@PathVariable Integer libraryId,
            @PathVariable Integer gameId) {
        Optional<ItemLibraryDTO> itemLibrary = itemLibraryService.findByLibraryId_IdAndGameId_Id(libraryId, gameId)
                .map(itemLibraryMapper::toDTO);
        return itemLibrary.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/library/{libraryId}/search/{name}")
    public ResponseEntity<List<ItemLibraryDTO>> findByLibraryAndName(@PathVariable Integer libraryId,
            @PathVariable String name) {
        List<ItemLibraryDTO> itemLibraries = itemLibraryService.findByLibraryAndName(libraryId, name).stream()
                .map(itemLibraryMapper::toDTO)
                .toList();
        return ResponseEntity.ok(itemLibraries);
    }

    @PostMapping
    public ResponseEntity<?> createItemLibrary(@RequestBody ItemLibraryDTO itemLibraryDTO) {
        try {
            ItemLibrary newItemLibrary = itemLibraryService.save(itemLibraryMapper.toEntity(itemLibraryDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(itemLibraryMapper.toDTO(newItemLibrary));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItemLibrary(@PathVariable Integer id) {
        try {
            itemLibraryService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
