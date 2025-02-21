package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Long> {
}
