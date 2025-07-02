package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.*;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.mapper.UserMapper;
import com.team3.api_collab_dev.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "users")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = new ArrayList<>();
        this.userService.getAllUsers().forEach(users::add);
        return ResponseEntity.ok(users);
    }
    @PostMapping
    public  ResponseEntity<User> createUser(@RequestBody @Valid UserCreateDTO user){

        return  ResponseEntity.status(HttpStatus.CREATED).body(this.userService.createUser(user));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> login(@RequestBody @Valid LoginDto login) {
        User user = userService.login(login.email(), login.password());
        UserResponseDTO response = userMapper.userToDto(user);
        return ResponseEntity.ok(response);
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/{userId}/changePassword" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ApiReponse> changePassword(@PathVariable(name = "userId") Long userId, @RequestBody @Valid ChangePasswordDTO passwordDTO){
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiReponse(String.valueOf(HttpStatus.ACCEPTED.value()),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                this.userService.changePassword(userId, passwordDTO)));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/updateUserInfo/{userId}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ApiReponse> updateUserInfo(@PathVariable(name = "userId") Long userId, @RequestBody @Valid UserUpdateDTO updateDTO){
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiReponse(String.valueOf(HttpStatus.ACCEPTED.value()),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                this.userService.updateUserInfo(userId, updateDTO)));
    }

}
