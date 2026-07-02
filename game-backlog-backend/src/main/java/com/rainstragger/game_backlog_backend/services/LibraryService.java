package com.rainstragger.game_backlog_backend.services;

import com.rainstragger.game_backlog_backend.entities.Library;
import com.rainstragger.game_backlog_backend.repository.LibraryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Library> findAll (boolean active){
        return libraryRepository.findAllByActive(active);
    }

    public List<Library> findByTerm (String term){
        return libraryRepository.findByTerm(term);
    }

    public Optional<Library> findById(Integer id) {
        return libraryRepository.findById(id);
    }

    public Optional<Library> findByName(String name) {
        return libraryRepository.findByName(name);
    }

    public Library create (Library library){
        if (library.getId() != null) {
            throw new IllegalArgumentException("Library already exists!");
        }else{
            if (library.getActive() == null) {
                library.setActive(true);
            }
            return libraryRepository.save(library);
        }
    }

    public Library update (Integer id, Library library){
        Library existent = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Library not found!"));
        existent.setName(library.getName());
        existent.setDescription(library.getDescription());
        existent.setActive(library.getActive());
        return libraryRepository.save(existent);
    }

    public void remove (Integer id){
        Library library = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Library not found!"));
        
        //Soft Delete
        library.setActive(false);
        libraryRepository.save(library);
    }
}
