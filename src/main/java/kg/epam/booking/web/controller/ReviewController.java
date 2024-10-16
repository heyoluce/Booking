package kg.epam.booking.web.controller;

import kg.epam.booking.domain.entities.Review;
import kg.epam.booking.service.ReviewService;
import kg.epam.booking.web.dto.ReviewDto;
import kg.epam.booking.web.mappers.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @GetMapping
    public ResponseEntity<Page<ReviewDto>> getAllReviews(Pageable pageable) {
        Page<Review> reviews = reviewService.getAllReviews(pageable);
        Page<ReviewDto> reviewDtos = reviews.map(reviewMapper::toDto);
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(reviewMapper.toDto(review));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Page<ReviewDto>> getReviewsByHotelId(@PathVariable Long hotelId, Pageable pageable) {
        Page<Review> reviews = reviewService.getReviewByHotelId(hotelId, pageable);
        Page<ReviewDto> reviewDtos = reviews.map(reviewMapper::toDto);
        return ResponseEntity.ok(reviewDtos);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewMapper.toEntity(reviewDto);
        Review createdReview = reviewService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.toDto(createdReview));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        Review review = reviewMapper.toEntity(reviewDto);
        Review updatedReview = reviewService.updateReview(id, review);
        return ResponseEntity.ok(reviewMapper.toDto(updatedReview));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
