package com.folksdev.movie.service;

import com.folksdev.movie.model.Actor;
import com.folksdev.movie.model.Gender;
import com.folksdev.movie.repository.ActorRepository;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

class ActorServiceTest {

    private ActorRepository actorRepository;

    private ActorService actorService;

    @BeforeEach
    public void setup() {
        actorRepository = Mockito.mock(ActorRepository.class);

        actorService = new ActorService(actorRepository);
    }

    @Test
    public void testGetActorList_whenIdListExist_shouldReturnActorList() {
        List<Actor> actorList = List.of(
                new Actor("id1","actorName1", LocalDate.EPOCH, Gender.MALE, new HashSet<>()),
                new Actor("id2","actorName2", LocalDate.EPOCH, Gender.FEMALE, new HashSet<>())
        );
        List<String> idList = List.of("id1", "id2");

        Mockito.when(actorRepository.findAllByIdIn(idList)).thenReturn(actorList);

        List<Actor> result = actorService.getActorList(idList);

        Assertions.assertEquals(actorList, result);

        Mockito.verify(actorRepository).findAllByIdIn(idList);
    }

    @Test
    public void testGetActorList_whenIdListNotExist_shouldReturnDefaultActorList() {
        List<Actor> actorList = List.of(
                new Actor("id",
                        "taner",
                        LocalDate.of(1997, 04,23),
                        Gender.MALE)
        );
        List<String> idList = List.of("id1", "id2");

        Mockito.when(actorRepository.findAllByIdIn(idList)).thenReturn(Collections.emptyList());

        List<Actor> result = actorService.getActorList(idList);

        Assertions.assertEquals(actorList, result);

        Mockito.verify(actorRepository).findAllByIdIn(idList);
    }

}