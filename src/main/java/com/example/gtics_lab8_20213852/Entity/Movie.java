package com.example.gtics_lab8_20213852.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "titulo", nullable = false, length = 45)
    private String titulo;

    @Column(name = "overview", nullable = false, length = 45)
    private String overview;

    @Column(name = "popularidad", nullable = false)
    private Double popularidad;

    @Column(name = "fecha_lanzamiento", nullable = false)
    private String fechaLanzamiento;

}