package com.folksdev.movie;

import com.folksdev.movie.model.Director;
import com.folksdev.movie.model.GenresType;
import com.folksdev.movie.model.Movie;
import com.folksdev.movie.model.Publisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class MovieApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MovieApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
    }


}
