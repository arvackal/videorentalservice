package com.example.videorentalservice.service;

import com.example.videorentalservice.dto.NewRentalRequest;
import com.example.videorentalservice.dto.RentalDetailsResponse;
import com.example.videorentalservice.entity.Rental;
import com.example.videorentalservice.entity.Role;
import com.example.videorentalservice.entity.User;
import com.example.videorentalservice.entity.Video;
import com.example.videorentalservice.exception.RentalAlreadyReturnedException;
import com.example.videorentalservice.exception.RentalNotFoundException;
import com.example.videorentalservice.exception.RentalNotPossibleException;
import com.example.videorentalservice.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService{
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserService userSerice;

    @Autowired
    private VideoService videoService;

    @Override
    public Rental getById(Long id, String currentUserEmail) {
        User user = userSerice.getByEmail(currentUserEmail);

        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException(id));

        if(rental.getCustomer() != user
            && user.getRole() != Role.ADMIN){
            throw new RentalNotFoundException(id);
        }

        return rental;

    }

    @Override
    public List<RentalDetailsResponse> getAll(String currentUserEmail) {

        User user = userSerice.getByEmail(currentUserEmail);

        if(user.getRole() != Role.ADMIN)
            throw new AccessDeniedException("Access denied.");

        return rentalRepository.findAll().stream().map(RentalDetailsResponse::new).toList();
    }

    @Override
    public List<RentalDetailsResponse> getAllByUser(String email) {
        User user = userSerice.getByEmail(email);

        List<Rental> rentals = rentalRepository.findByCustomer(user);

        return rentals.stream().map(RentalDetailsResponse::new).toList();
    }


    @Override
    public RentalDetailsResponse addRental(String currentUserEmail, NewRentalRequest request) {
        User user = userSerice.getByEmail(currentUserEmail);
        Video video = videoService.getById(request.getVideo());

        if(rentalRepository.existsByCustomerAndVideoAndReturnedFalse(user,video)){
            throw new RentalNotPossibleException("Rental of this video already exist.");
        }

        Rental rental = Rental.builder()
                .customer(user)
                .video(video)
                .rentalDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(10))
                .returned(false)
                .build();

        rentalRepository.save(rental);
        return new RentalDetailsResponse(rental);
    }

    @Transactional
    @Override
    public RentalDetailsResponse returnRental(Long id, String currentUserEmail) {
        Rental rental = getById(id, currentUserEmail);

        if(rental.getReturned())
            throw new RentalAlreadyReturnedException(id);

        rental.setReturned(true);
        rental.setReturnDate(LocalDateTime.now());

        return new RentalDetailsResponse(rental);
    }
}
