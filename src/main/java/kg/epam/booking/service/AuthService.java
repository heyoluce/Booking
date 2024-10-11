package kg.epam.booking.service;

import kg.epam.booking.domain.entities.user.User;
import kg.epam.booking.web.auth.JwtRequest;
import kg.epam.booking.web.auth.JwtResponse;
import kg.epam.booking.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtResponse login(final JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userService.getByUsername(loginRequest.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(), user.getUsername(), user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                user.getId(), user.getUsername()));
        return jwtResponse;
    }

    public JwtResponse refresh(final String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

}
