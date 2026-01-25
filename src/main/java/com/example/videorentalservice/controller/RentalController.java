package com.example.videorentalservice.controller;

import com.example.videorentalservice.dto.NewRentalRequest;
import com.example.videorentalservice.dto.RentalDetailsResponse;
import com.example.videorentalservice.entity.Rental;
import com.example.videorentalservice.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RentalDetailsResponse>> getAllRentals(Authentication authentication){
        String currentUserEmail = authentication.getName();

        List<RentalDetailsResponse> responses = rentalService.getAll(currentUserEmail);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public  ResponseEntity<List<RentalDetailsResponse>> getAllByUser(Authentication authentication){
        String currentUserEmail = authentication.getName();

        List<RentalDetailsResponse> responses = rentalService.getAllByUser(currentUserEmail);

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RentalDetailsResponse> getById(@PathVariable Long id,
                                Authentication authentication){

        String currentUserEmail = authentication.getName();
        Rental rental = rentalService.getById(id, currentUserEmail);

        return ResponseEntity.ok(new RentalDetailsResponse(rental));
    }



    @PostMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<RentalDetailsResponse> createRental(
            @Valid @RequestBody NewRentalRequest request,
            Authentication authentication){
        String currentUserEmail = authentication.getName();

        RentalDetailsResponse response = rentalService.addRental(currentUserEmail, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/me/{id}/return")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<RentalDetailsResponse> returnRental(
            @PathVariable Long id,
            Authentication authentication
            ){
        String currentUserEmail = authentication.getName();

        RentalDetailsResponse response = rentalService.returnRental(id, currentUserEmail);

        return ResponseEntity.ok(response);
    }


}
