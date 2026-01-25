package com.example.videorentalservice.controller;

import com.example.videorentalservice.dto.AddVideoRequest;
import com.example.videorentalservice.dto.UpdateVideoRequest;
import com.example.videorentalservice.dto.VideoDetailsResponse;
import com.example.videorentalservice.entity.Video;
import com.example.videorentalservice.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/{id}")
    public ResponseEntity<VideoDetailsResponse> getVideoById(@Valid @PathVariable Long id){
        Video video = videoService.getById(id);
        VideoDetailsResponse response = new VideoDetailsResponse(video);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VideoDetailsResponse>> getAllVideos(){
        List<VideoDetailsResponse> responses = videoService.getAll();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VideoDetailsResponse> addVideo(
            @Valid @RequestBody AddVideoRequest request,
            Authentication authentication
    ){
        String currentUserEmail = authentication.getName();

        VideoDetailsResponse response = videoService.addVideo(currentUserEmail, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteVideo(@PathVariable Long id,
                            Authentication authentication){
        String currentUserEmail = authentication.getName();

        videoService.deleteVideoById(id,currentUserEmail);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VideoDetailsResponse> updateVideo(@PathVariable Long id,
                                                            @RequestBody UpdateVideoRequest request,
                                                            Authentication authentication){
        String currentUserEmail = authentication.getName();

        VideoDetailsResponse response = videoService.updateVideo(id, currentUserEmail, request);

        return ResponseEntity.ok(response);
    }

}
