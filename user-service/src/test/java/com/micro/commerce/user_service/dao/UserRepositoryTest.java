package com.micro.commerce.user_service.dao;

import com.micro.commerce.user_service.entity.Role;
import com.micro.commerce.user_service.entity.User;
import com.micro.commerce.user_service.util.RoleName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Role adminRole;
    private Role employeeRole;
    Role customerRole;

    @BeforeAll
    void initRoles() {
        // Persist roles only once
        adminRole = roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        employeeRole = roleRepository.save(new Role(RoleName.ROLE_EMPLOYEE));
        customerRole = roleRepository.save(new Role(RoleName.ROLE_CUSTOMER));
    }

    public void createUser() {
        Set<Role> adminrRoleSet = new HashSet<>();
        adminrRoleSet.add(adminRole);

        Set<Role> user1RoleSet = new HashSet<>();
        user1RoleSet.add(employeeRole);
        user1RoleSet.add(customerRole);

        Set<Role> user2RoleSet = new HashSet<>();
        user2RoleSet.add(adminRole);

        User adminUser = User.builder()
                .userName("admin")
                .email("admin@example.com")
                .password("password")
                .roles(adminrRoleSet)
                .firstName("admin")
                .lastName("MC")
                .build();

        User user1 = User.builder()
                .userName("user1")
                .email("user1@example.com")
                .password("password")
                .roles(user1RoleSet)
                .firstName("User1")
                .lastName("MC")
                .build();

        User user2 = User.builder()
                .userName("user2")
                .email("user2@example.com")
                .password("password")
                .roles(user2RoleSet)
                .firstName("User2")
                .lastName("MC")
                .build();

        userRepository.save(adminUser);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1"})
    public void testFindByUsername(String userName){
        createUser();

        // Fetch user
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            User userDetails = user.get();
            assertThat(userDetails.getUsername()).isEqualTo("user1");
            assertThat(userDetails.getPassword()).isEqualTo("password");
            assertThat(userDetails.getEmail()).isEqualTo("user1@example.com");
            assertThat(userDetails.getFirstName()).isEqualTo("User1");
            assertThat(userDetails.getLastName()).isEqualTo("MC");
            assertThat(userDetails.getRoles())
                    .hasSize(2)
                    .extracting(Role::getRoleName)
                    .containsExactlyInAnyOrder(RoleName.ROLE_EMPLOYEE, RoleName.ROLE_CUSTOMER);

        }

    }

    @Test
    public void testUserNotFound() {
        Optional<User> user = userRepository.findByUserName("userN");
        assertThat(user).isEmpty();
    }

    @Test
    public void testAllUser() {
        createUser();
        List<User> user = userRepository.findAll();
        assertThat(user).hasSize(3);
    }

}
