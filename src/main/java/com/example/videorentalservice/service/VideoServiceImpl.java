package com.example.videorentalservice.service;

import com.example.videorentalservice.dto.AddVideoRequest;
import com.example.videorentalservice.dto.UpdateVideoRequest;
import com.example.videorentalservice.dto.VideoDetailsResponse;
import com.example.videorentalservice.entity.Genre;
import com.example.videorentalservice.entity.Role;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.entity.Video;
import com.example.videorentalservice.exception.UnknownGenreException;
import com.example.videorentalservice.exception.VideoNotFoundException;
import com.example.videorentalservice.repository.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;

    private Boolean isAdmin(Role role){
        return role == Role.ADMIN;
    }

    private Genre parseGenre(String genreString){
        if(genreString.trim().isEmpty())
            throw new UnknownGenreException(genreString);

        try{
            return Genre.valueOf(genreString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownGenreException(genreString);
        }
    }

    @Override
    public Video getById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));

        return video;
    }

    @Override
    public List<VideoDetailsResponse> getAllByUser(String email) {
        User user = userService.getByEmail(email);

        if(!isAdmin(user.getRole()))
            throw new AccessDeniedException("Access denied.");

        return videoRepository.findByAddedBy(user).stream().map(VideoDetailsResponse::new).toList();
    }

    @Override
    public VideoDetailsResponse addVideo(String currentUserEmail, AddVideoRequest request) {
        User user = userService.getByEmail(currentUserEmail);

        if(!isAdmin(user.getRole()))
            throw new AccessDeniedException("Access denied.");

        Genre genre = parseGenre(request.getGenre());

        System.out.println("Time : " + LocalDateTime.now());

        Video video = Video.builder()
                .title(request.getTitle())
                .director(request.getDirector())
                .genre(genre)
                .available(true)
                .addedAt(LocalDateTime.now())
                .addedBy(user)
                .build();


        videoRepository.save(video);

        return new VideoDetailsResponse(video);
    }

    @Override
    public List<VideoDetailsResponse> getAll() {
        return videoRepository.findAll().stream().map(VideoDetailsResponse::new).toList();
    }

    @Override
    public void deleteVideoById(Long id, String currentUserEmail) {
        User user = userService.getByEmail(currentUserEmail);

        if(!isAdmin(user.getRole()))
            throw new AccessDeniedException("Access denied.");

        Video video = getById(id);

        videoRepository.delete(video);
    }

    @Transactional
    @Override
    public VideoDetailsResponse updateVideo(Long id, String currentUserEmail, UpdateVideoRequest request) {
        User user = userService.getByEmail(currentUserEmail);

        if(!isAdmin(user.getRole()))
            throw new AccessDeniedException("Access denied.");

        Video video = getById(id);
        Genre genre = parseGenre(request.getGenre());

        video.setTitle(request.getTitle());
        video.setGenre(genre);
        video.setDirector(request.getDirector());

        return new VideoDetailsResponse(video);
    }
}
