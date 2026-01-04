package com.micro.commerce.user_service.dto;

import java.util.Set;

public record UserDto(String userName, String password, String email, String firstName, String lastName, Set<String> roles) { }
