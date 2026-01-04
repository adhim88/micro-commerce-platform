package com.micro.commerce.user_service.dao;

import com.micro.commerce.user_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
