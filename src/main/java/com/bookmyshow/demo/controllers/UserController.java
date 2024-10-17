package com.bookmyshow.demo.controllers;

import com.bookmyshow.demo.dtos.*;
import com.bookmyshow.demo.exceptions.UserAlreadyExisting;
import com.bookmyshow.demo.exceptions.UserDoesNotExist;
import com.bookmyshow.demo.models.User;
import com.bookmyshow.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/signup")
    public SignupUserResponseDto signup(@RequestBody SignupUserRequestDto requestDto){
        User user;
        try {
            user = userService.signup(requestDto);
        } catch (UserAlreadyExisting e) {
            throw new RuntimeException(e);
        }
        return new SignupUserResponseDto(user.getId(), ResponseStatus.SUCCESS);
    }

    @GetMapping("/login")
    public boolean login(@RequestBody LoginUserRequestDto requestDto){
        try {
            return userService.login(requestDto.getEmail(), requestDto.getPassword());
        } catch (UserDoesNotExist e) {
            throw new RuntimeException(e);
        }
    }
}