package com.folksdev.movie.configuration;

import com.folksdev.movie.model.*;
import com.folksdev.movie.repository.ActorRepository;
import com.folksdev.movie.repository.DirectorRepository;
import com.folksdev.movie.repository.MovieRepository;
import com.folksdev.movie.repository.PublisherRepository;
import com.folksdev.movie.service.MovieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.folksdev.movie.model.Gender.*;

/**
 * !!!! IMPORTANT INFORMATION !!!!
 * THIS CLASS CAN BE ELIGIBLE JUST FOR H2 in-memory DB
 * If you already change your configuration to switch DB system with
 * persistence DB (such as PostgreSQL or MySQL) you must either
 * comment out this `run` method or remove the whole class.
 */
@Component
@ConditionalOnProperty(name = "command.line.runner.enable", havingValue = "true")
public class DataLoader implements CommandLineRunner {

    private final PublisherRepository publisherRepository;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final MovieService movieService;

    public DataLoader(PublisherRepository publisherRepository, ActorRepository actorRepository, MovieRepository movieRepository, DirectorRepository directorRepository, MovieService movieService) {
        this.publisherRepository = publisherRepository;
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.movieService = movieService;
    }


    @Override
    public void run(String... args) throws Exception {

        /* This line will provide more reliable Data loading process on booting of application */
        if (!movieRepository.findAll().isEmpty()) {
            return;
        }

        Actor actor1 = new Actor("Scarlett Johansson", parseDate("22-11-1984"), Gender.FEMALE);
        Actor actor2 = new Actor("Morgan Freeman", LocalDate.of(1937, 6, 1), Gender.MALE);

        Set<Actor> actors1 = actorRepository.saveAll(Arrays.asList(actor1, actor2)).stream().collect(Collectors.toSet());

        Publisher publisher1 = new Publisher("Virginie Besson-Silla");
        publisher1 = publisherRepository.save(publisher1);

        Publisher publisher2 = new Publisher("Marvel");
        publisher2 = publisherRepository.save(publisher2);

        Publisher publisher3 = new Publisher("Truenorth Productions");
        publisher3 = publisherRepository.save(publisher3);

        Director d1 = new Director("Cate", "Shortland");
        Director d2 = new Director("Malcolm","Spellman");
        List<Director> directors = directorRepository.saveAll(Arrays.asList(d1, d2));


        Movie movie1 = new Movie(
                "Lucy",
                "A woman, accidentally caught in a dark deal, turns the tables on her captors and transforms into a merciless warrior evolved beyond human logic.",
                "https://www.imdb.com/title/tt2872732/",
                89,
                2014,
                Arrays.asList(GenresType.ACTION, GenresType.SCI_FI, GenresType.THRILLER),
                actors1,
                directors.get(0),
                publisher1
                );
        movie1 = movieRepository.save(movie1);


        Movie movie2 = new Movie(
                "Black Widow",
                "Natasha Romanoff confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises.",
                "https://www.imdb.com/title/tt3480822/",
                134,
                2021,
                Arrays.asList(GenresType.ACTION, GenresType.SCI_FI, GenresType.THRILLER),
                actors1,
                directors.get(0),
                publisher2);
        movieRepository.save(movie2);


        Movie movie3 = new Movie(
                "The Falcon and the Winter Soldier",
                "Following the events of 'Avengers: Endgame,' Sam Wilson/Falcon and Bucky Barnes/Winter Soldier team up in a global adventure that tests their abilities -- and their patience.",
                "https://www.imdb.com/title/tt9208876/",
                50,
                2021,
                Arrays.asList(GenresType.ACTION, GenresType.SCI_FI, GenresType.THRILLER),
                actors1,
                directors.get(0),
                publisher3);

        movieRepository.save(movie3);

        //System.out.println(movieService.findImdbUrlById(movie1.getId()));


       /* movieService.createMovie(new CreateMovieRequest(movie1.getTitle(),
                movie1.getDescription(),
                movie1.getImdbUrl(),
                movie1.getDuration(),
                movie1.getFeaturedYear(),
                movie1.getGenresType(),
                movie1.getActors().stream().map(a -> a.getId()).collect(Collectors.toSet()),
                movie1.getPublisher().getId()));*/

    }

    private LocalDate parseDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);

        return LocalDate.parse(date, formatter);
    }
}
