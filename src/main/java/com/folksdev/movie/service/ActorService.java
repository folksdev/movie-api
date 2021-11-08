package com.folksdev.movie.service;

import com.folksdev.movie.model.Actor;
import com.folksdev.movie.model.Gender;
import com.folksdev.movie.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    protected List<Actor> getActorList(List<String> idList){
        return Optional.of(actorRepository.findAllByIdIn(idList))
                .filter(a -> !a.isEmpty())
                .orElse(List.of(
                        new Actor("id",
                                "taner",
                                LocalDate.of(1997, 04,23),
                                Gender.MALE)
                ));
    }
}
