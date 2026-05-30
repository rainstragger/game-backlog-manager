package com.rainstragger.game_backlog_backend.entities;

import java.math.BigInteger;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cover_media")
public class CoverMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "storage_key", nullable = false)
    private String storageKey;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "checksum", nullable = false)
    private String checksum;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private BigInteger sizeBytes;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }
    
    public String getStorageKey() {
        return storageKey;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
    
    public String getChecksum() {
        return checksum;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setSizeBytes(BigInteger sizeBytes) {
        this.sizeBytes = sizeBytes;
    }
    
    public BigInteger getSizeBytes() {
        return sizeBytes;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
}