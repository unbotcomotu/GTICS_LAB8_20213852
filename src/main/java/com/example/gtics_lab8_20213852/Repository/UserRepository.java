package com.example.gtics_lab8_20213852.Repository;

import com.example.gtics_lab8_20213852.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByNombre(String nombre);
}