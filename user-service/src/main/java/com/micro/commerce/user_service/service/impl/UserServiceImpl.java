package com.micro.commerce.user_service.service.impl;

import com.micro.commerce.user_service.dao.RoleRepository;
import com.micro.commerce.user_service.dao.UserRepository;
import com.micro.commerce.user_service.dto.UserDto;
import com.micro.commerce.user_service.dto.UserUpdateDto;
import com.micro.commerce.user_service.entity.Role;
import com.micro.commerce.user_service.entity.User;
import com.micro.commerce.user_service.mapper.UserMapper;
import com.micro.commerce.user_service.service.UserService;
import com.micro.commerce.user_service.util.RoleName;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto) {
        // 1. Dto to Entity
        User userDetails = userMapper.toUserEntity(userDto, passwordEncoder, roleRepository);

        // 2. Save User
        User user = userRepository.save(userDetails);

        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto getUserByUserName(String userName) {
        UserDto userDto = null;
        if(userRepository.findByUserName(userName).isPresent()) {
            User user = userRepository.findByUserName(userName).get();
            userDto = userMapper.toUserDto(user);
        }
        return userDto;
    }

    @Override
    public UserDto updateUserByUserName(String userName, UserUpdateDto userUpdateDto) {
        UserDto userdto = null;
        if(userRepository.findByUserName(userName).isPresent()) {
            User user = userRepository.findByUserName(userName).get();

            User updatedUser = new User.UserBuilder(user)
                    .email(userUpdateDto.email() != null ? userUpdateDto.email() : user.getEmail())
                    .firstName(userUpdateDto.firstName() != null ? userUpdateDto.firstName() : user.getFirstName())
                    .lastName(userUpdateDto.lastName() != null ? userUpdateDto.lastName() : user.getLastName())
                    .build();

            user.apply(updatedUser);
            userdto = userMapper.toUserDto(user);
        }

        return userdto;
    }

    @Override
    public void deleteUserByUserName(String userName) {
        if (userRepository.findByUserName(userName).isPresent()) {
            User user = userRepository.findByUserName(userName).get();
            userRepository.delete(user);
        } else {
            throw new UsernameNotFoundException("UserName not found!");
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByRoleName(RoleName.valueOf(name))
                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
    }
}
