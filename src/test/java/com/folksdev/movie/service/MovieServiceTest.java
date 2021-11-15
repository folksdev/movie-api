package com.folksdev.movie.service;

import com.folksdev.movie.TestSupport;
import com.folksdev.movie.dto.ActorDto;
import com.folksdev.movie.dto.CreateMovieRequest;
import com.folksdev.movie.dto.DirectorDto;
import com.folksdev.movie.dto.MovieDto;
import com.folksdev.movie.dto.converter.MovieDtoConverter;
import com.folksdev.movie.model.*;
import com.folksdev.movie.repository.MovieRepository;
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
    private MovieDtoConverter movieDtoConverter;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository = Mockito.mock(MovieRepository.class);
        actorService = Mockito.mock(ActorService.class);
        publisherService = Mockito.mock(PublisherService.class);
        directorService = Mockito.mock(DirectorService.class);
        movieDtoConverter = Mockito.mock(MovieDtoConverter.class);

        movieService = new MovieService(movieRepository, actorService, publisherService, directorService, movieDtoConverter);
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
        Movie movie = generateMovie("",
                movieRequest,
                Set.of(new Actor("id","taner", LocalDate.of(1997, 04,23), Gender.MALE, Collections.emptySet())));
        Movie expectedMovie = generateMovie("id",
                movieRequest,
                Set.of(new Actor("id","taner", LocalDate.of(1997, 04,23), Gender.MALE, Collections.emptySet())));

        MovieDto expectedMovieDto = new MovieDto("id",
                "title",
                "description",
                "imdbUrl",
                "1h40",
                100,
                List.of(GenresType.ACTION, GenresType.COMEDY),
                List.of(new ActorDto("id", "taner", LocalDate.of(1997, 04,23), Gender.MALE)),
                new DirectorDto("directorId","directorName", "directorLastName"),
                "publisherName"
                );

        /* 2. Adim: Davranis belirleme (Mock siniflar icin) */
        Mockito.when(publisherService.getPublisherById("publisherId")).thenReturn(generatePublisher());
        Mockito.when(directorService.getDirectorById("directorId")).thenReturn(generateDirector());
        Mockito.when(actorService.getActorList(List.of("actorId1", "actorId2"))).thenReturn(List.of(new Actor("id","taner", LocalDate.of(1997, 04,23), Gender.MALE, Collections.emptySet())));
        Mockito.when(movieRepository.save(movie)).thenReturn(expectedMovie);
        Mockito.when(movieDtoConverter.convert(expectedMovie)).thenReturn(expectedMovieDto);


        /* 3. Adim: Test edilecek metodu calistir */
        MovieDto result = movieService.createMovie(movieRequest);

        /* 4. Adim: Sonucu, beklenen veri ile karsilastir. */

        assertEquals(expectedMovieDto, result);

        /* 5. Adim: hangi davranışlar gerçekleştirilmiş kontrol et */
        Mockito.verify(publisherService).getPublisherById("publisherId");
        Mockito.verify(directorService).getDirectorById("directorId");
        Mockito.verify(actorService).getActorList(List.of("actorId1", "actorId2"));
        Mockito.verify(movieRepository).save(movie);
        Mockito.verify(movieDtoConverter).convert(expectedMovie);
    }

}