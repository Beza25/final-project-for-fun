package com.example.demo.business.data.repository;

import com.example.demo.business.data.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(String role);
}
