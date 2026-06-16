package com.cg4all.registration_model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

import com.cg4all.registration_model.entities.User;


@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String Email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmailToken(String id);
}
