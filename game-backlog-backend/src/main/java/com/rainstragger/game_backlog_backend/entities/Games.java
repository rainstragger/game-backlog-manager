package com.rainstragger.game_backlog_backend.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "games")
public class Games {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Game's name is required")
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private Boolean active;

	@Column(name = "resume")
	private String resume;

	@Column(name = "launch_date")
	private LocalDate launchDate;

	@Column(name = "developer")
	private String developer;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "genre")
	private String genre;

	@Column(name = "min_required")
	private String minReq;

	@Column(name = "recom_required")
	private String recomReq;

	@Column(name = "cover_id")
	private Integer coverId;

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public LocalDate getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(LocalDate launchDate) {
		this.launchDate = launchDate;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getMinReq() {
		return minReq;
	}

	public void setMinReq(String minReq) {
		this.minReq = minReq;
	}

	public String getRecomReq() {
		return recomReq;
	}

	public void setRecomReq(String recomReq) {
		this.recomReq = recomReq;
	}

	public Integer getCoverId() {
		return coverId;
	}

	public void setCoverId(Integer coverId) {
		this.coverId = coverId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
