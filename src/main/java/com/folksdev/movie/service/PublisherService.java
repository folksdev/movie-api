package com.folksdev.movie.service;

import com.folksdev.movie.model.Publisher;
import com.folksdev.movie.repository.PublisherRepository;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    protected Publisher getPublisherById(String id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
    }
}
