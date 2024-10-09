package kg.epam.booking.service;

import kg.epam.booking.domain.entities.Review;
import kg.epam.booking.domain.exception.ResourceNotFoundException;
import kg.epam.booking.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Page<Review> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Page<Review> getReviewByHotelId(Long hotelId, Pageable pageable) {
        return reviewRepository.findByHotelId(hotelId, pageable);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review review) {
        review.setId(id);
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

}
