package com.folksdev.movie.repository;

import com.folksdev.movie.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, String> {
}
