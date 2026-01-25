package com.example.videorentalservice.dto;

import com.example.videorentalservice.entity.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVideoRequest {
    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Director is required.")
    private String director;

    @NotBlank(message = "Genre is required.")
    private String genre;
}
