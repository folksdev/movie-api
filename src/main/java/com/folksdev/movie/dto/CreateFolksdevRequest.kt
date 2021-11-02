package com.folksdev.movie.dto

import javax.validation.constraints.*

data class CreateFolksdevRequest(

    @field:NotBlank
    val id: String,
    @field:NotBlank(message = "the name value must not be empty or null")
    val name: String,
    @field:NotNull
    @field:Max(value = 100)
    @field:Min(value = 1)
    @field:Positive
    val age: Int,
    @field:Email
    val email: String)