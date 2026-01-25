package com.example.videorentalservice.dto;

import com.example.videorentalservice.entity.Rental;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.entity.Video;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDetailsResponse {
    private Long id;
    private UserDetailsResponse customer;
    private VideoDetailsResponse video;
    private Boolean isReturned;

    public RentalDetailsResponse(Rental rental) {
        this.id = rental.getId();
        this.customer = new UserDetailsResponse(rental.getCustomer());
        this.video = new VideoDetailsResponse(rental.getVideo());
        this.isReturned = rental.getReturned();
    }
}
