package com.folksdev.movie.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.folksdev.movie.model.Gender
import java.time.LocalDate

data class ActorDto @JvmOverloads constructor(
    val id: String,
    val name: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val movieList: List<MovieDto>? = ArrayList()
)

/*
{
"id": "ja123123",
"name" : "Furkan"
"dateOfBirthday" : "22/10/1988"
"gender": MALE,
"movieList": [{}]
}
 */
