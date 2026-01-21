package com.example.videorentalservice.repository;

import com.example.videorentalservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
