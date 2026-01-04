package com.micro.commerce.user_service.dao;

import com.micro.commerce.user_service.dto.UserDto;
import com.micro.commerce.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

}
