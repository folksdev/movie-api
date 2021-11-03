package com.folksdev.movie.service;

import com.folksdev.movie.dto.CreateMovieRequest;
import com.folksdev.movie.model.*;
import com.folksdev.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                        ActorService actorService,
                        PublisherService publisherService,
                        DirectorService directorService) { //Memory address
        this.movieRepository = movieRepository;
        this.actorService = actorService;
        this.publisherService = publisherService;
        this.directorService = directorService;
    }


    public Movie createMovie(CreateMovieRequest movieRequest) {

        Publisher publisher = publisherService.getPublisherById(movieRequest.getPublisherId());
        Director director = directorService.getDirectorById(movieRequest.getDirectorId());
        List<Actor> actorList = actorService.getActorList(movieRequest.getActors());
        if(actorList.isEmpty()) {
            actorList.add(new Actor("taner", LocalDate.of(1997, 04,23), Gender.MALE));
        }
        Movie movie = new Movie(
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
