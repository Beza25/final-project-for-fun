package com.example.demo.business.data.repository;

import com.example.demo.business.data.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    Long countByEmail(String email);

    Set<User> findAllByFollowers(User user);

    Set<User> findAllByFollowings(User user);

    ArrayList<User> findByUsernameContainingIgnoreCase(String s1);

    ArrayList<User> findAll();
}
