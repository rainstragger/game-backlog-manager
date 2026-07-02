package com.rainstragger.game_backlog_backend.controller;

import com.rainstragger.game_backlog_backend.dto.LibraryDTO;
import com.rainstragger.game_backlog_backend.entities.Library;
import com.rainstragger.game_backlog_backend.services.LibraryService;
import com.rainstragger.game_backlog_backend.mappers.LibraryMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;
    private final LibraryMapper libraryMapper;

    public LibraryController(LibraryService libraryService, LibraryMapper libraryMapper) {
        this.libraryService = libraryService;
        this.libraryMapper = libraryMapper;
    }

    @GetMapping
    public ResponseEntity<List<LibraryDTO>> findAll(){
        List<LibraryDTO> library = libraryService.findAll(true).stream()
            .map(libraryMapper::toDTO)
            .toList();

        return ResponseEntity.ok(library);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryDTO> findbyId(@PathVariable Integer id){
        Optional<LibraryDTO> library = libraryService.findById(id)
                .map(libraryMapper::toDTO);
        return library.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<List<LibraryDTO>> findbyTerm(@PathVariable String term){
        List<LibraryDTO> library = libraryService.findByTerm(term).stream()
                .map(libraryMapper::toDTO)
                .toList();
        return ResponseEntity.ok(library);
    }

    @PostMapping
    public ResponseEntity<?> createLibrary (@RequestBody LibraryDTO libraryDTO){
        try{
            Library newLibrary = libraryService.create(libraryMapper.toEntity(libraryDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(libraryMapper.toDTO(newLibrary));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibrary (@PathVariable Integer id, @RequestBody LibraryDTO libraryDTO){
        try{
            Library updatedLibrary = libraryService.update(id, libraryMapper.toEntity(libraryDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(libraryMapper.toDTO(updatedLibrary));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeLibrary (@PathVariable Integer id){
        try{
            libraryService.remove(id);
            return ResponseEntity.noContent().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 
