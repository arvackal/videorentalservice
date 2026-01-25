package com.example.videorentalservice.service;


import com.example.videorentalservice.dto.NewRentalRequest;
import com.example.videorentalservice.dto.RentalDetailsResponse;
import com.example.videorentalservice.entity.Rental;

import java.util.List;

public interface RentalService {
    public Rental getById(Long id, String currentUserEmail);
    public List<RentalDetailsResponse> getAll(String currentUserEmail);
    public List<RentalDetailsResponse> getAllByUser(String email);
    public RentalDetailsResponse addRental(String currentUserEmail, NewRentalRequest request);
    public RentalDetailsResponse returnRental(Long id, String currentUserEmail);
}
