package com.micro.commerce.user_service.dao;

import com.micro.commerce.user_service.entity.Role;
import com.micro.commerce.user_service.util.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);

    Set<Role> findByRoleNameIn(Set<RoleName> roleNames);
}
