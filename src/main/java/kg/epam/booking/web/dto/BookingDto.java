package kg.epam.booking.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingDto {
    private Long id;

    @NotNull(message = "Hotel ID must not be null")
    private Long hotelId;

    @NotNull(message = "User ID must not be null")
    private Long userId;

    private BigDecimal totalPrice;

    @NotNull(message = "Check-in date must not be null")
    @Future(message = "Check-in date must be in the future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date must not be null")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    private boolean isActive;
}