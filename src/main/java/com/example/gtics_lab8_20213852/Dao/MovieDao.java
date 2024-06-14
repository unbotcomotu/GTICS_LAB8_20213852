package com.example.gtics_lab8_20213852.Dao;

import com.example.gtics_lab8_20213852.Entity.Movie;
import com.example.gtics_lab8_20213852.Entity.NowPlayingResults;
import com.example.gtics_lab8_20213852.Entity.results;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class MovieDao {


    public List<String> nowPlaying(String api_key){
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

    public Movie buscarPorTitulo(String titulo,String api_key){
        RestTemplate restTemplate=new RestTemplate();
        Movie movie=new Movie();
        try{
            //esto se usa para disponer de las 500 páginas de búsqueda y así contar con el resultado
            for(int page=1;page<=500;page++){
                ResponseEntity<NowPlayingResults> response=restTemplate.getForEntity("https://api.themoviedb.org/3/discover/movie?api_key="+api_key+"&page="+page, NowPlayingResults.class);
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
            }
            return null;
        }catch (HttpClientErrorException.NotFound ex){
            return null;
        }
    }
}
