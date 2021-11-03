package com.folksdev.movie.service;

import com.folksdev.movie.model.Director;
import com.folksdev.movie.repository.DirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    protected Director getDirectorById(String id){
        return directorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("asd"));
    }
}
