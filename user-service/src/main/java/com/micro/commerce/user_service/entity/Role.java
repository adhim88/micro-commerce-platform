package com.micro.commerce.user_service.entity;

import com.micro.commerce.user_service.dao.RoleRepository;
import com.micro.commerce.user_service.util.RoleName;
import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName roleName;

    public RoleName getRoleName() {
        return roleName;
    }

    protected Role() { //JPA

    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }
}
