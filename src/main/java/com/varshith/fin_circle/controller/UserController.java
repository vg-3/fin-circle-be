package com.varshith.fin_circle.controller;

import com.varshith.fin_circle.dto.UpdateUserRequestBody;
import com.varshith.fin_circle.dto.UserRegistrationDto;
import com.varshith.fin_circle.entity.User;
import com.varshith.fin_circle.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }

    @PostMapping()
    public User registerUser(@RequestBody UserRegistrationDto userRegistrationDto){
        return userService.registerUser(userRegistrationDto);
    }

//    @PutMapping()
//    public User updateUser(@RequestBody UpdateUserRequestBody updateUserRequestBody){
//        return userService.updateUser(updateUserRequestBody);
//    }

}
