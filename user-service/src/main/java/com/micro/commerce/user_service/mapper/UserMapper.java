package com.micro.commerce.user_service.mapper;

import com.micro.commerce.user_service.dao.RoleRepository;
import com.micro.commerce.user_service.dto.UserDto;
import com.micro.commerce.user_service.entity.Role;
import com.micro.commerce.user_service.entity.User;
import com.micro.commerce.user_service.util.RoleName;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", builder = @Builder(buildMethod = "build"))
public interface UserMapper {

    @Mapping(target = "roles", expression =  "java(mapStringsToRoles(userDto.roles(),roleRepository))")
    @Mapping(target = "password", ignore = true)
    User toUserEntity(UserDto userDto, @Context PasswordEncoder passwordEncoder, @Context RoleRepository roleRepository);

    default Set<Role> mapStringsToRoles(Set<String> roleNames, @Context RoleRepository roleRepository) {
        if (roleNames == null) return Collections.emptySet();
        return roleNames.stream()
                .map(roleName -> roleRepository.findByRoleName(RoleName.valueOf(roleName))
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());
    }

    @AfterMapping
    default void encodePassword(UserDto dto, @MappingTarget User.UserBuilder builder, @Context PasswordEncoder passwordEncoder) {
        if (dto.password() != null) {
            builder.password(passwordEncoder.encode(dto.password()));
        }
    }

    @Mapping(target = "roles", expression = "java(mapRolesToStrings(user.getRoles()))")
    @Mapping(source = "userName", target = "userName")
    UserDto toUserDto(User user);

    default Set<String> mapRolesToStrings(Set<Role> roles) {
        if (roles == null) return Collections.emptySet();
        return roles.stream()
                .map(role -> role.getRoleName().name()) // or role.getName()
                .collect(Collectors.toSet());
    }
}
