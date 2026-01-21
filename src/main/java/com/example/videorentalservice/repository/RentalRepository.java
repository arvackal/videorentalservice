package com.example.videorentalservice.repository;

import com.example.videorentalservice.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
