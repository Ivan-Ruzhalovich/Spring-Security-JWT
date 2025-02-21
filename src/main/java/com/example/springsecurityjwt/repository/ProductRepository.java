package com.example.springsecurityjwt.repository;

import com.example.springsecurityjwt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
