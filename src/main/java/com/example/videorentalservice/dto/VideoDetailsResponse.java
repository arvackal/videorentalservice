package com.example.videorentalservice.dto;

import com.example.videorentalservice.entity.Genre;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.entity.Video;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoDetailsResponse {
    private Long id;
    private String title;
    private String director;
    private Genre genre;
    private LocalDateTime addedAt;

    public VideoDetailsResponse(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.director = video.getDirector();
        this.genre = video.getGenre();
        this.addedAt = video.getAddedAt();
    }
}
