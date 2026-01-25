package com.example.videorentalservice.repository;

import com.example.videorentalservice.entity.Rental;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    public List<Rental> findByCustomer(User customer);
    public List<Rental> findByCustomerAndVideoAndReturnedFalse(User customer, Video video);
    public Boolean existsByCustomerAndVideoAndReturnedFalse(User customer, Video video);
}
