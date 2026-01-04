package com.micro.commerce.user_service.entity;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    private User userId;
    private String phone;
    private String address;
}
