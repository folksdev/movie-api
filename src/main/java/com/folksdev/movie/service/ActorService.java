package com.folksdev.movie.service;

import com.folksdev.movie.model.Actor;
import com.folksdev.movie.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    protected List<Actor> getActorList(List<String> idList){
        return actorRepository.findAllByIdIn(idList);
    }
}
