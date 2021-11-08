package com.folksdev.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.folksdev.movie.dto.converter.MovieDtoConverter;
import com.folksdev.movie.model.Actor;
import com.folksdev.movie.model.Gender;
import com.folksdev.movie.repository.ActorRepository;
import com.folksdev.movie.repository.DirectorRepository;
import com.folksdev.movie.repository.MovieRepository;
import com.folksdev.movie.repository.PublisherRepository;
import com.folksdev.movie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@DirtiesContext
@AutoConfigureMockMvc
public class IntegrationTestSupport {

    @Autowired
    public MovieService movieService;

    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public ActorRepository actorRepository;

    @Autowired
    public PublisherRepository publisherRepository;

    @Autowired
    public DirectorRepository directorRepository;

    @Autowired
    public MovieDtoConverter movieDtoConverter;

    public final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
    }

    public List<Actor> generateActors(int size){
        return IntStream.range(0, size)
                .mapToObj( this::generateActor)
                .collect(Collectors.toList());
    }

    public Actor generateActor(int i){
        return new Actor("actor-name-"+i, LocalDate.of(1999, 12, 30), Gender.MALE);
    }
}
