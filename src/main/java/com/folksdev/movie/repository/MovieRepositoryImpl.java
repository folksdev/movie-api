package com.folksdev.movie.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements CustomMovieRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<String> findImdbUrlByMovieId(String id) {
        Query query = entityManager.createNativeQuery("SELECT m.imdb_url FROM Movie as m WHERE m.movie_id=:id");
        query.setParameter("id", id);

        return Optional.ofNullable((String)query.getSingleResult());
    }
}
