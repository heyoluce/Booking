package kg.epam.booking.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReviewDto {
    private Long id;

    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Hotel ID must not be null")
    private Long hotelId;

    @Positive(message = "Rating must be positive")
    private int rating;

    private String comment;
}