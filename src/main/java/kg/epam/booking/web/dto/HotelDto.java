package kg.epam.booking.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {

    @JsonProperty(
            access = JsonProperty.Access.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "Hotel name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Invalid price format")
    private BigDecimal pricePerNight;

    @Min(value = 0, message = "Rating cannot be negative")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private double rating;

    @Min(value = 1, message = "Total rooms must be at least 1")
    private int totalRooms;

    @Min(value = 0, message = "Booked rooms cannot be negative")
    private int bookedRooms;

}
