package com.folksdev.movie.repository;

import com.folksdev.movie.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
}
