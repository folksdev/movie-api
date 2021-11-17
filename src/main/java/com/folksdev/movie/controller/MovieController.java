package com.folksdev.movie.controller;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.folksdev.movie.dto.ActorDto;
import com.folksdev.movie.dto.CreateMovieRequest;
import com.folksdev.movie.dto.MovieDto;
import com.folksdev.movie.model.Movie;
import com.folksdev.movie.service.MovieService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies() {
        List<MovieDto> movieDtoList = movieService.getAllMovies();
        movieDtoList.forEach( movieDto -> {
            Link movieLink = linkTo(methodOn(MovieController.class).getMovie(movieDto.getId())).withSelfRel();
            movieDto.add(movieLink);
            getActorLinks(movieDto.getActors());
        });

        return ResponseEntity.ok(movieDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable String id) {
        MovieDto movieDto = movieService.getMovieById(id);
        getActorLinks(movieDto.getActors());
        return ResponseEntity.ok(movieDto);
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody CreateMovieRequest movieRequest) {
        return ResponseEntity.ok(movieService.createMovie(movieRequest));
    }

    private void getActorLinks(List<ActorDto> actorDtoList) {
        actorDtoList.forEach(actorDto -> {
            Link actorLink = linkTo(methodOn(ActorController.class).getActorById(actorDto.getId())).withSelfRel();
            actorDto.add(actorLink);
        });
    }
}
