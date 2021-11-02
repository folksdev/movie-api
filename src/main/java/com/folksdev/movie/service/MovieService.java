package com.folksdev.movie.service;

import com.folksdev.movie.dto.CreateMovieRequest;
import com.folksdev.movie.model.Actor;
import com.folksdev.movie.model.Director;
import com.folksdev.movie.model.Movie;
import com.folksdev.movie.model.Publisher;
import com.folksdev.movie.repository.DirectorRepository;
import com.folksdev.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieService {

    private final MovieRepository movieRepository; // Immutable => Testability, Thread safety
    private final ActorService actorService;
    private final PublisherService publisherService;
    private final DirectorService directorService;


    // constructor based => BEST PRACTICE
    public MovieService(MovieRepository movieRepository,
                        DirectorRepository directorRepository, ActorService actorService, PublisherService publisherService, DirectorService directorService) { //Memory address
        this.movieRepository = movieRepository;
        this.actorService = actorService;
        this.publisherService = publisherService;
        this.directorService = directorService;
    }


    public Movie createMovie(CreateMovieRequest movieRequest) {
        Publisher publisher = publisherService.getPublisherById(movieRequest.getPublisherId());
        Director director = directorService.findDirectorById(movieRequest.getDirectorId());
        List<Actor> actorList = actorService.getActorList(List.copyOf(movieRequest.getActors()));
        Movie movie = new Movie(null,
                movieRequest.getTitle(),
                movieRequest.getDescription(),
                movieRequest.getImdbUrl(),
                movieRequest.getDuration(),
                movieRequest.getFeaturedYear(),
                movieRequest.getGenresType(),
                Set.copyOf(actorList),
                director,
                publisher);
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public String findImdbUrlById(String id) {
        return movieRepository.selectImdbUrlByMovieId(id)
                .orElseThrow(() -> new RuntimeException("FUCK!"));
    }
}
