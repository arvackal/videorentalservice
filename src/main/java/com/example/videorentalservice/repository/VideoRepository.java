package com.example.videorentalservice.repository;

import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    public List<Video> findByAddedBy(User user);
}
