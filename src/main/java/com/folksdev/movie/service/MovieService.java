package com.folksdev.movie.service;

import com.folksdev.movie.dto.CreateMovieRequest;
import com.folksdev.movie.dto.MovieDto;
import com.folksdev.movie.dto.converter.MovieDtoConverter;
import com.folksdev.movie.exception.MovieNotFoundException;
import com.folksdev.movie.model.*;
import com.folksdev.movie.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository; // Immutable => Testability, Thread safety
    private final ActorService actorService;
    private final PublisherService publisherService;
    private final DirectorService directorService;
    private final MovieDtoConverter movieDtoConverter;

    // constructor based => BEST PRACTICE
    public MovieService(MovieRepository movieRepository,
                        ActorService actorService,
                        PublisherService publisherService,
                        DirectorService directorService, MovieDtoConverter movieDtoConverter) { //Memory address
        this.movieRepository = movieRepository;
        this.actorService = actorService;
        this.publisherService = publisherService;
        this.directorService = directorService;
        this.movieDtoConverter = movieDtoConverter;
    }

    public MovieDto createMovie(CreateMovieRequest movieRequest) {

        Publisher publisher = publisherService.getPublisherById(movieRequest.getPublisherId());
        Director director = directorService.getDirectorById(movieRequest.getDirectorId());
        Set<Actor> actorList = actorService.getActorList(movieRequest.getActorIds()).stream().collect(Collectors.toSet());

        logger.info("Publisher, director and actorList received");
        Movie movie = new Movie(
                movieRequest.getTitle(),
                movieRequest.getDescription(),
                movieRequest.getImdbUrl(),
                movieRequest.getDuration(),
                movieRequest.getFeaturedYear(),
                movieRequest.getGenresType(),
                actorList,
                director,
                publisher);

        return movieDtoConverter.convert(movieRepository.save(movie));
    }

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll()
                .stream().map(movieDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public String findImdbUrlById(String id) {
        return movieRepository.selectImdbUrlByMovieId(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie could not find by id: " + id));
    }

    public MovieDto getMovieById(String id) {
        return movieDtoConverter.convert(findMovieById(id));
    }

    protected Movie findMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie could not find by id: " + id));
    }
}
