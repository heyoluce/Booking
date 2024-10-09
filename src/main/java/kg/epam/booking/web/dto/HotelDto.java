package kg.epam.booking.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HotelDto {
    private Long id;

    @NotBlank(message = "Hotel name must not be empty")
    private String name;

    @NotBlank(message = "City must not be empty")
    private String city;

    private String description;

    @Positive(message = "Price per night must be positive")
    private double pricePerNight;

    @Positive(message = "Rating must be positive")
    private double rating;

    @Positive(message = "Total rooms must be positive")
    private int totalRooms;

    @Positive(message = "Booked rooms must be positive")
    private int bookedRooms;
}
