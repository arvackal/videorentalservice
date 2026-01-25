package com.example.videorentalservice.dto;

import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.entity.Video;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class NewRentalRequest {

    @NonNull
    private Long video;
}
