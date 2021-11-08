package com.folksdev.movie.controller;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.folksdev.movie.dto.CreateMovieRequest;
import com.folksdev.movie.dto.MovieDto;
import com.folksdev.movie.model.Movie;
import com.folksdev.movie.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable String id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody CreateMovieRequest movieRequest) {
        return ResponseEntity.ok(movieService.createMovie(movieRequest));
    }
}
