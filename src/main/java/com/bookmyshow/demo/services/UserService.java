package com.bookmyshow.demo.services;

import com.bookmyshow.demo.dtos.SignupUserRequestDto;
import com.bookmyshow.demo.exceptions.UserAlreadyExisting;
import com.bookmyshow.demo.models.User;
import com.bookmyshow.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    public User signup(SignupUserRequestDto requestDto) throws UserAlreadyExisting {
        Optional<User> ExistingUser = userRepository.findByEmail(requestDto.getEmail());
        if (ExistingUser.isPresent()) {
            throw new UserAlreadyExisting();
        }
        User newUser = new User();
        newUser.setEmail(requestDto.getEmail());
        newUser.setName(requestDto.getName());
        String password = requestDto.getPassword();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(password));

        return userRepository.save(newUser);

    }
}
