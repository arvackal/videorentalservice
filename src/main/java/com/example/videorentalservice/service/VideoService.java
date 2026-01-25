package com.example.videorentalservice.service;


import com.example.videorentalservice.dto.AddVideoRequest;
import com.example.videorentalservice.dto.UpdateVideoRequest;
import com.example.videorentalservice.dto.VideoDetailsResponse;
import com.example.videorentalservice.entity.Video;

import java.util.List;

public interface VideoService {
    public Video getById(Long id);
    public List<VideoDetailsResponse> getAllByUser(String email);
    public VideoDetailsResponse addVideo(String currentUserEmail, AddVideoRequest request);
    public List<VideoDetailsResponse> getAll();
    public void deleteVideoById(Long id, String curretUserEmail);
    public VideoDetailsResponse updateVideo(Long id, String currentUserEmail, UpdateVideoRequest request);
}
