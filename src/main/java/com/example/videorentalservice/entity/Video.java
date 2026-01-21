package com.example.videorentalservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Video {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "addedBy")
    private User addedBy;

    @Column(nullable = false)
    private LocalDateTime addedAt;

}
