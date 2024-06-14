package com.example.gtics_lab8_20213852.Dao;

import com.example.gtics_lab8_20213852.Entity.Movie;
import com.example.gtics_lab8_20213852.Entity.NowPlayingResults;
import com.example.gtics_lab8_20213852.Entity.results;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class MovieDao {
    private final String api_key="f2b24e2cd1e7c4a9db0e1cff9d9f2560";

    public List<String> nowPlaying(){
        RestTemplate restTemplate=new RestTemplate();
        try{
            ResponseEntity<NowPlayingResults> response=restTemplate.getForEntity("https://api.themoviedb.org/3/movie/now_playing?api_key="+api_key, NowPlayingResults.class);
            List<String> listaPeliculas=new ArrayList<>();
            for(results result:response.getBody().getResults()){
                listaPeliculas.add(result.getOriginal_title());
            }
            return listaPeliculas;
        }catch (HttpClientErrorException.NotFound ex){
            return null;
        }
    }

    public Movie buscarPorTitulo(String titulo){
        RestTemplate restTemplate=new RestTemplate();
        Movie movie=new Movie();
        try{
            ResponseEntity<NowPlayingResults> response=restTemplate.getForEntity("https://api.themoviedb.org/3/discover/movie?api_key="+api_key, NowPlayingResults.class);
            for(results result:response.getBody().getResults()){
                String tituloEncontrado=result.getOriginal_title();
                if(tituloEncontrado.contains(titulo)){
                    movie.setTitulo(result.getOriginal_title());
                    movie.setOverview(result.getOverview());
                    movie.setPopularidad(result.getPopularity());
                    movie.setFechaLanzamiento(result.getRelease_date());
                    return movie;
                }
            }
            return null;
        }catch (HttpClientErrorException.NotFound ex){
            return null;
        }
    }
}
