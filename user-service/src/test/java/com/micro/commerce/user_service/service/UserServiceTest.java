package com.micro.commerce.user_service.service;

import com.micro.commerce.user_service.dao.RoleRepository;
import com.micro.commerce.user_service.dao.UserRepository;
import com.micro.commerce.user_service.dto.UserDto;
import com.micro.commerce.user_service.entity.Role;
import com.micro.commerce.user_service.entity.User;
import com.micro.commerce.user_service.mapper.UserMapper;
import com.micro.commerce.user_service.service.impl.UserServiceImpl;
import com.micro.commerce.user_service.util.RoleName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    //@Test
    public void testCreateUser() {
        //Input DTO
        UserDto userDto = new UserDto(
                "admin",
                "password",
                "admin@example.com",
                "Admin",
                "MC",
                Set.of("ROLE_ADMIN")
        );

        //Mapper returns User entity
        User mappedUser = new User("admin", "admin@example.com");
        when(userMapper.toUserEntity(userDto, passwordEncoder, roleRepository)).thenReturn(mappedUser);

        //Password encoder
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        //Role repository (optional if service resolves roles)
        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        when(roleRepository.findByRoleName(RoleName.ROLE_ADMIN)).thenReturn(Optional.of(adminRole));

        //Repository save returns the saved user
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //Invoke
        UserDto result = userService.createUser(userDto);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.userName()).isEqualTo("admin");
        assertThat(result.roles()).contains("ROLE_ADMIN");

        //Verify interactions
        verify(userMapper).toUserEntity(userDto, passwordEncoder,roleRepository);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));

    }
}
