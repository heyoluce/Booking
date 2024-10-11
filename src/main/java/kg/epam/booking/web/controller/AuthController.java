package kg.epam.booking.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.epam.booking.domain.entities.user.User;
import kg.epam.booking.service.AuthService;
import kg.epam.booking.service.UserService;
import kg.epam.booking.web.auth.JwtRequest;
import kg.epam.booking.web.auth.JwtResponse;
import kg.epam.booking.web.dto.UserDto;
import kg.epam.booking.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody final JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated @RequestBody final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody final String refreshToken) {
        return authService.refresh(refreshToken);
    }

}
