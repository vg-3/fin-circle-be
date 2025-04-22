package com.varshith.fin_circle.service;

import com.varshith.fin_circle.dto.UpdateUserRequestBody;
import com.varshith.fin_circle.dto.UserRegistrationDto;
import com.varshith.fin_circle.entity.User;
import com.varshith.fin_circle.exception.DuplicateResourceException;
import com.varshith.fin_circle.exception.ResourceNotFound;
import com.varshith.fin_circle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    get User
    public User getUser(Integer id){
       return userRepository.findById(id).orElseThrow(()-> new ResourceNotFound("User not found"));
    }

//    save user
    public User registerUser(UserRegistrationDto userRegistrationDto){

        System.out.println(userRegistrationDto.user().email());

      Optional<User> savedUser=  userRepository.findByEmail(userRegistrationDto.user().email());

      if(savedUser.isPresent()){
        throw new DuplicateResourceException("User already exists");
      }

        System.out.println(userRegistrationDto.user().email());

      User user = User.builder()
                .email(userRegistrationDto.user().email())
                .password(userRegistrationDto.user().password())
              .name(userRegistrationDto.user().name())
              .phoneNumber(userRegistrationDto.user().phoneNumber())
                .userDetails(userRegistrationDto.userDetails()).build();

      return userRepository.save(user);
    }

//    update user
//    public User updateUser(UpdateUserRequestBody updateUserRequestBody){
//        Optional<User> savedUser=  userRepository.findById(updateUserRequestBody.id());
//        if
//        return userRepository.save(user);
//    }

}
