package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<OurUsers,Integer> {
    Optional<OurUsers> findByEmail(String email);
}
