package com.example.gtics_lab8_20213852.Controller;

import com.example.gtics_lab8_20213852.Dao.MovieDao;
import com.example.gtics_lab8_20213852.Entity.Movie;
import com.example.gtics_lab8_20213852.Entity.User;
import com.example.gtics_lab8_20213852.Repository.MovieRepository;
import com.example.gtics_lab8_20213852.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class KiwiController {

    final private MovieDao movieDao;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final String api_key="f2b24e2cd1e7c4a9db0e1cff9d9f2560";

    public KiwiController(MovieDao movieDao,
                          UserRepository userRepository,
                          MovieRepository movieRepository) {
        this.movieDao = movieDao;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("nowPlaying")
    public ResponseEntity<HashMap<String, Object>> nowPlaying() {
        HashMap<String, Object> response = new HashMap<>();
        List<String> listaPeliculas= movieDao.nowPlaying(api_key);
        response.put("status","success");
        response.put("content", listaPeliculas);
        return ResponseEntity.ok(response);
    }

    @PostMapping("guardarFavorito")
    public ResponseEntity<HashMap<String, Object>> guardarFavorito(@RequestParam(value = "titulo",required = false)String titulo,
                                                                                  @RequestParam(value = "nombreUsuario",required = false)String nombreUsuario) {
        HashMap<String, Object> response = new HashMap<>();
        HashMap<String, Object> errors = new HashMap<>();
        Boolean validacion=true;
        if(titulo==null||titulo.isEmpty()){
            response.put("status","error");
            errors.put("titulo","Ingrese el título de la película");
            validacion=false;
        }
        if(nombreUsuario==null||nombreUsuario.isEmpty()){
            response.put("status","error");
            errors.put("nombreUsuario","Ingrese el nombreDeUsuario");
            validacion=false;
        }
        Movie movie= movieDao.buscarPorTitulo(titulo,api_key);
        User user=userRepository.findByNombre(nombreUsuario);
        if(movie==null){
            response.put("status","error");
            errors.put("titulo","El título ingresado no existe o no se relaciona a algún resultado");
            validacion=false;
        }
        if(user==null){
            response.put("status","error");
            errors.put("nombreUsuario","El nombre de usuario ingresado no se relaciona a alguno que se encuentre registrado");
            validacion=false;
        }
        if(validacion){
            movie.setUser(user);
            movieRepository.save(movie);
            response.put("status","success");
            response.put("content", movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

}
