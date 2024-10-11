package kg.epam.booking.web.mappers;

import kg.epam.booking.domain.entities.Booking;
import kg.epam.booking.web.dto.BookingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper extends Mappable<Booking, BookingDto> {
}
