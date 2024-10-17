package com.bookmyshow.demo.services;

import com.bookmyshow.demo.dtos.SignupUserRequestDto;
import com.bookmyshow.demo.exceptions.UserAlreadyExisting;
import com.bookmyshow.demo.exceptions.UserDoesNotExist;
import com.bookmyshow.demo.models.User;
import com.bookmyshow.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private String userNotFound = "User Does Not Exist";

    public User signup(SignupUserRequestDto requestDto) throws UserAlreadyExisting {
        Optional<User> checkUser = userRepository.findByEmail(requestDto.getEmail());
        if (checkUser.isPresent()) {
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

    public boolean login(String email, String password) throws UserDoesNotExist {

        Optional<User> ExistingUser = userRepository.findByEmail(email);
        if (ExistingUser.isEmpty()) {
            throw new UserDoesNotExist(userNotFound);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, ExistingUser.get().getPassword());
    }
}
