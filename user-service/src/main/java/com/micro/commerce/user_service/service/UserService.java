package com.micro.commerce.user_service.service;

import com.micro.commerce.user_service.dto.UserDto;
import com.micro.commerce.user_service.dto.UserUpdateDto;
import com.micro.commerce.user_service.entity.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public UserDto createUser(UserDto userDto);

    public List<UserDto> getAllUsers();

    public UserDto getUserByUserName(String userName);

    public UserDto updateUserByUserName(String userName, UserUpdateDto userUpdateDto);

    public void deleteUserByUserName(String userName);

    public Role getRoleByName(String name);
}
