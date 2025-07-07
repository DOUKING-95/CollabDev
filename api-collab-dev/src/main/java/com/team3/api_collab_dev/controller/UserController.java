package com.team3.api_collab_dev.controller;

import com.team3.api_collab_dev.dto.*;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.ProfilType;
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
    public ResponseEntity<ApiReponse<?>> getUsers(){
        List<User> users = new ArrayList<>();
        this.userService.getAllUsers().forEach(users::add);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        users
                )
        );
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ApiReponse<?>> createUser(@RequestBody @Valid UserCreateDTO user){

        return  ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.CREATED.value()),
                        HttpStatus.CREATED.getReasonPhrase(),
                        this.userService.createUser(user)
                )
        );
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiReponse<?>> login(@RequestBody @Valid LoginDto login) {
        User user = userService.login(login.email(), login.password());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.userService.createUser(this.userMapper.userToCreateDto(user))
                )
        );
    }
    @ResponseStatus(value = HttpStatus.ACCEPTED, code = HttpStatus.ACCEPTED)
    @PutMapping(path = "/{userId}/changePassword" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ApiReponse<?>> changePassword(@PathVariable(name = "userId") Long userId, @RequestBody @Valid ChangePasswordDTO passwordDTO){
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiReponse<>(String.valueOf(HttpStatus.ACCEPTED.value()),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                this.userService.changePassword(userId, passwordDTO)));
    }

    @ResponseStatus(value = HttpStatus.ACCEPTED,code = HttpStatus.ACCEPTED)
    @PutMapping(path = "/updateUserInfo/{userId}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ApiReponse<?>> updateUserInfo(@PathVariable(name = "userId") Long userId, @RequestBody @Valid UserUpdateDTO updateDTO){
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiReponse<>(String.valueOf(HttpStatus.ACCEPTED.value()),
                HttpStatus.ACCEPTED.getReasonPhrase(),
                this.userService.updateUserInfo(userId, updateDTO)));
    }

    @GetMapping(path = "/{userId}")
    public  ResponseEntity<ApiReponse<?>> getUserById(@PathVariable(name = "userId") Long userId){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.userService.getUserById(userId)
                )
        );

    }
    //TODO: A tester cette endpoint demain
    @PutMapping(path = "joinProjectWithProfilName")
    private ResponseEntity<ApiReponse<?>> joinProjectWithProfilName (Long userId, ProfilType profilType, Long projectId){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiReponse<>(
                        String.valueOf(HttpStatus.ACCEPTED.value()),
                        HttpStatus.ACCEPTED.getReasonPhrase(),
                        this.userService.joinProjectWithProfilName(userId,profilType,projectId)
                )
        );
    }
}
