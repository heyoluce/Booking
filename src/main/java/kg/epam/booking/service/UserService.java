package kg.epam.booking.service;

import kg.epam.booking.domain.entities.user.User;
import kg.epam.booking.domain.enums.Role;
import kg.epam.booking.domain.exception.ResourceNotFoundException;
import kg.epam.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    //   @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    //   @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    //    @Caching(put = {
//            @CachePut(value = "UserService::getById",
//                    key = "#user.id"),
//            @CachePut(value = "UserService::getByUsername",
//                    key = "#user.username")
//    })
    public User update(final User user) {
        //    user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    //    @Caching(cacheable = {
//            @Cacheable(value = "UserService::getById",
//                    key = "#user.id"),
//            @Cacheable(value = "UserService::getByUsername",
//                    key = "#user.username")
//    })
    public User create(final User user) {
        if (userRepository.findByUsername(
                user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }


    //    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }
}