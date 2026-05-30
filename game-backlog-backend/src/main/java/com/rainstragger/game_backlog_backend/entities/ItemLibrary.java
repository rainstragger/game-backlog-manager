package com.rainstragger.game_backlog_backend.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_library")
public class ItemLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "game_id", nullable = false)
    private Integer gameId;

    @Column(name = "library_id", nullable = false)
    private Integer libraryId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "completed_at")
    private LocalDate completedAt;

    @Column(name = "started_at")
    private LocalDate startedAt;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public Integer getLibraryId() {
        return libraryId;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
    }
    
    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }
    
    public LocalDate getStartedAt() {
        return startedAt;
    }

}
