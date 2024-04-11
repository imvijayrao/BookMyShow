package com.bookmyshow.demo.controllers;

import com.bookmyshow.demo.dtos.ResponseStatus;
import com.bookmyshow.demo.dtos.SignupUserRequestDto;
import com.bookmyshow.demo.dtos.SignupUserResponseDto;
import com.bookmyshow.demo.exceptions.UserAlreadyExisting;
import com.bookmyshow.demo.models.User;
import com.bookmyshow.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public SignupUserResponseDto signup(SignupUserRequestDto requestDto){
        User user = null;
        try {
            user = userService.signup(requestDto);
        } catch (UserAlreadyExisting e) {
            throw new RuntimeException(e);
        }
        return new SignupUserResponseDto(user.getId(), ResponseStatus.SUCCESS);
    }
}
