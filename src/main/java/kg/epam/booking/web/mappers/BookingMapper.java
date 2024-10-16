package kg.epam.booking.web.mappers;

import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.web.dto.BookingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapper extends Mappable<Booking, BookingDto> {

    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "user.id", target = "userId")
    BookingDto toDto(Booking booking);

    @Mapping(source = "hotelId", target = "hotel.id")
    @Mapping(source = "userId", target = "user.id")
    Booking toEntity(BookingDto bookingDto);

    List<BookingDto> toDto(List<Booking> bookings);
}
