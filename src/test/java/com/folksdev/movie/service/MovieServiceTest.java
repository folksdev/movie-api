package com.folksdev.movie.service;

import com.folksdev.movie.TestSupport;
import com.folksdev.movie.dto.CreateMovieRequest;
import com.folksdev.movie.model.*;
import com.folksdev.movie.repository.MovieRepository;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest extends TestSupport {

    private MovieRepository movieRepository; // Immutable => Testability, Thread safety
    private ActorService actorService;
    private PublisherService publisherService;
    private DirectorService directorService;

    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository = Mockito.mock(MovieRepository.class);
        actorService = Mockito.mock(ActorService.class);
        publisherService = Mockito.mock(PublisherService.class);
        directorService = Mockito.mock(DirectorService.class);

        movieService = new MovieService(movieRepository, actorService, publisherService, directorService);
    }

    @Test
    void testCreateMovie_whenPublisherIdNotExist_shouldThrowRuntimeException() {
        CreateMovieRequest movieRequest = generateMovieRequest();

        Mockito.when(publisherService.getPublisherById("publisherId")).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class,
                () -> movieService.createMovie(movieRequest));

        Mockito.verify(publisherService).getPublisherById("publisherId");
        Mockito.verifyNoInteractions(actorService);
        Mockito.verifyNoInteractions(directorService);
        Mockito.verifyNoInteractions(movieRepository);
    }

    @Test
    void testCreateMovie_whenPublisherIdExistAndDirectorIdNotExist_shouldThrowRuntimeException() {
        CreateMovieRequest movieRequest = generateMovieRequest();
        Publisher publisher = generatePublisher();

        Mockito.when(publisherService.getPublisherById("publisherId")).thenReturn(publisher);
        Mockito.when(directorService.getDirectorById("directorId")).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class,
                () -> movieService.createMovie(movieRequest));

        Mockito.verify(publisherService).getPublisherById("publisherId");
        Mockito.verify(directorService).getDirectorById("directorId");
        Mockito.verifyNoInteractions(actorService);
        Mockito.verifyNoInteractions(movieRepository);
    }

    @Test
    void testCreateMovie_whenActorListNotExist_shouldReturnCreatedMovieByDefaultActor() {
        /* 1. Adim: Veri Hazirlama */
        CreateMovieRequest movieRequest = generateMovieRequest();
        Movie movie = generateMovie(null,
                movieRequest,
                Set.of(new Actor("taner", LocalDate.of(1997, 04,23), Gender.MALE)));
        Movie expectedMovie = generateMovie("id",
                movieRequest,
                Set.of(new Actor("taner", LocalDate.of(1997, 04,23), Gender.MALE)));

        /* 2. Adim: Davranis belirleme (Mock siniflar icin) */
        Mockito.when(publisherService.getPublisherById("publisherId")).thenReturn(generatePublisher());
        Mockito.when(directorService.getDirectorById("directorId")).thenReturn(generateDirector());
        Mockito.when(actorService.getActorList(List.of("actorId1", "actorId2"))).thenReturn(new ArrayList<>());
        Mockito.when(movieRepository.save(movie)).thenReturn(expectedMovie);

        /* 3. Adim: Test edilecek metodu calistir */
        Movie result = movieService.createMovie(movieRequest);

        /* 4. Adim: Sonucu, beklenen veri ile karsilastir. */

        assertEquals(expectedMovie, result);

        /* 5. Adim: hangi davranışlar gerçekleştirilmiş kontrol et */
        Mockito.verify(publisherService).getPublisherById("publisherId");
        Mockito.verify(directorService).getDirectorById("directorId");
        Mockito.verify(actorService).getActorList(List.of("actorId1", "actorId2"));
        Mockito.verify(movieRepository).save(movie);
    }

}