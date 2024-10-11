package kg.epam.booking.web.mappers;

import kg.epam.booking.domain.entities.Hotel;
import kg.epam.booking.web.dto.HotelDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper extends Mappable<Hotel, HotelDto> {
}
