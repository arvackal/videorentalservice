package com.example.videorentalservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "video", nullable = false)
    private Video video;

    @Column(nullable = false)
    private LocalDateTime rentalDate;

    @Column(nullable = true)
    private LocalDateTime returnDate;

    @Column(nullable = false)
    private Boolean returned;

    @Column(nullable = false)
    private LocalDateTime dueDate;
}
