package com.rainstragger.game_backlog_backend.controller;

import com.rainstragger.game_backlog_backend.dto.LibraryDto;
import com.rainstragger.game_backlog_backend.model.Library;
import com.rainstragger.game_backlog_backend.services.LibraryService;
import com.rainstragger.game_backlog_backend.mappers.LibraryMappers;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;
    private final LibraryMappers libraryMappers;

    public LibraryController(LibraryService libraryService, LibraryMappers libraryMappers) {
        this.libraryService = libraryService;
        this.libraryMappers = libraryMappers;
    }

    @GetMapping
    public ResponseEntity<List<LibraryDto>> findAll(){
        List<LibraryDto> library = libraryService.findAll().stream()
            .map(libraryMappers::toDTO)
            .toList();

        return ResponseEntity.ok(library);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryDTO> findbyId(@PathVariable Integer id){
        Optional<LibraryDTO> library = libraryService.findById(id)
                .map(libraryMappers::toDTO);
        return library.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{term}")
    public ResponseEntity<List<LibraryDTO>> findbyTerm(@PathVariable String term){
        List<LibraryDTO> library = libraryService.findByTerm(term).stream()
                .map(libraryMappers::toDTO)
                .toList();
        return ResponseEntity.ok(library);
    }

    @PostMapping
    public ResponseEntity<?> createLibrary (@RequestBody LibraryDTO libraryDTO){
        try{
            Library newLibrary = libraryService.create(libraryMappers.toEntity(libraryDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(libraryMappers.toDTO(newLibrary));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibrary (@PathVariable Integer id, @RequestBody LibraryDTO libraryDTO){
        try{
            Library updatedLibrary = libraryService.update(id, libraryMappers.toEntity(libraryDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(libraryMappers.toDTO(updatedLibrary));
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
