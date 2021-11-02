package com.folksdev.movie.controller;

import com.folksdev.movie.dto.CreateFolksdevDto;
import com.folksdev.movie.dto.CreateFolksdevRequest;
import com.folksdev.movie.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/hello")
public class HelloController {

    //Create Read Update Delete
    @GetMapping
    public ResponseEntity<String> getFolksdev() {
        return ResponseEntity.ok("Hello Folksie!~");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getFolksevWithId(@PathVariable String id) {

        return ResponseEntity.ok("Hello Folksie! from ");
    }

    @PostMapping
    public ResponseEntity<CreateFolksdevDto> createFolksdev(@Valid @RequestBody CreateFolksdevRequest createFolksdevRequest) {
        int birthYear = 2021 - createFolksdevRequest.getAge();
        CreateFolksdevDto createFolksdevDto = new CreateFolksdevDto(createFolksdevRequest.getName(), birthYear);
        return new ResponseEntity<>(createFolksdevDto, HttpStatus.CREATED);

    }

    @PostMapping(value = "/taner")
    public ResponseEntity<String> createTaner(@RequestBody String id) {
        return new ResponseEntity<>("id: " + id + " is created", HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateFolksdev(@PathVariable String id, @RequestBody String a) {
        return ResponseEntity.ok("id: " + id + " is updated with " + a);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteFolksdev(@PathVariable String id) {
        return ResponseEntity.ok("id: " + id + " is created");
    }


}
