package com.folksdev.movie;

import com.folksdev.movie.dto.CreateMovieRequest;
import com.folksdev.movie.model.*;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestSupport {


    public CreateMovieRequest generateMovieRequest() {
        return new CreateMovieRequest("title",
                "description",
                "imbdbUrl",
                100,
                2020,
                List.of(GenresType.ACTION, GenresType.COMEDY),
                List.of("actorId1", "actorId2"),
                "publisherId",
                "directorId");
    }

    public Movie generateMovie(@Nullable String id,
                                CreateMovieRequest movieRequest,
                                Set<Actor> actors) {
        return new Movie(
                id,
                movieRequest.getTitle(),
                movieRequest.getDescription(),
                movieRequest.getImdbUrl(),
                movieRequest.getDuration(),
                movieRequest.getFeaturedYear(),
                movieRequest.getGenresType(),
                actors,
                generateDirector(),
                generatePublisher());
    }

    public Publisher generatePublisher() {
        return new Publisher("publisherId", "publisherName");
    }

    public Director generateDirector() {
        return new Director("directorId", "directorName", "directorLastName",
                Collections.emptySet());
    }

}
