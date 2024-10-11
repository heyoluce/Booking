package kg.epam.booking.web.mappers;

import kg.epam.booking.domain.entities.user.User;
import kg.epam.booking.web.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
