package com.micro.commerce.user_service.controller;

import com.micro.commerce.user_service.dto.UserDto;
import com.micro.commerce.user_service.dto.UserUpdateDto;
import com.micro.commerce.user_service.entity.User;
import com.micro.commerce.user_service.service.UserService;
import com.micro.commerce.user_service.service.impl.UserServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping
    public  ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        if( userDtos== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public  ResponseEntity<UserDto> getUserByUserName(@PathVariable String userName) {
        UserDto userDto = userService.getUserByUserName(userName);
        if( userDto== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PatchMapping("/{userName}")
    public ResponseEntity<UserDto> updateUserByUserName(@PathVariable String userName, @RequestBody UserUpdateDto userUpdateDto) {
        UserDto userDto = userService.updateUserByUserName(userName, userUpdateDto);
        if(userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("userName")
    public ResponseEntity<Void> deleteUserByUserName(@PathVariable String userName) {
        userService.deleteUserByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
