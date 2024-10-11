package kg.epam.booking.web.mappers;

import kg.epam.booking.domain.entities.Review;
import kg.epam.booking.web.dto.ReviewDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends Mappable<Review, ReviewDto> {
}
