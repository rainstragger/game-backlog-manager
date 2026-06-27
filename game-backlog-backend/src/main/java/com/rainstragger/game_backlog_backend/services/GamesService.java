package com.rainstragger.game_backlog_backend.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.rainstragger.game_backlog_backend.repository.GamesRepository;
import com.rainstragger.game_backlog_backend.entities.Games;

@Service
public class GamesService {

    private final GamesRepository gamesRepository;

    public GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public List <Games> findAll(boolean active) {
        return gamesRepository.findAllByActiveOrderByNameAsc(active);
    }

    public Optional<Games> findById(Integer id) {
        return gamesRepository.findById(id);
    }

    public Optional<Games> findByName(String name) {
        return gamesRepository.findByName(name);
    }

    public List<Games> findByTerm(String term) {
        return gamesRepository.findByTerm(term);
    }

    public Games create(Games game) {
        if (game.getId() != null) {
            throw new IllegalArgumentException("Jogo já existe");
        }
        if (game.getActive() == null) {
            game.setActive(true);
        }
        return gamesRepository.save(game);
    }

    public Games update(Integer id, Games games) {
		Games existente = findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));

		existente.setName(games.getName());
        existente.setActive(games.getActive());
        existente.setResume(games.getResume());
        existente.setLaunchDate(games.getLaunchDate());
        existente.setDeveloper(games.getDeveloper());
        existente.setPublisher(games.getPublisher());
        existente.setGenre(games.getGenre());
        existente.setMinReq(games.getMinReq());
        existente.setRecomReq(games.getRecomReq());
        existente.setCoverId(games.getCoverId());

		return gamesRepository.save(existente);
	}

    public void remove(Integer id) {
        Games game = findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));
        
        //Soft Delete
        game.setActive(false);
        gamesRepository.save(game);

    }
}
