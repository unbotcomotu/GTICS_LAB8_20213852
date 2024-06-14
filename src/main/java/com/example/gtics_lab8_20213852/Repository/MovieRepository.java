package com.example.gtics_lab8_20213852.Repository;

import com.example.gtics_lab8_20213852.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}