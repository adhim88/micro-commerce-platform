package com.micro.commerce.user_service.dao;

import com.micro.commerce.user_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
}
