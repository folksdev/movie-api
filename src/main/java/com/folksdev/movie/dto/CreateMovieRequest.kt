package com.folksdev.movie.dto

import com.folksdev.movie.model.GenresType

data class CreateMovieRequest(
    val title: String?,
    val description: String?,
    val imdbUrl: String?,
    val duration: Int?,
    val featuredYear: Int?,
    val genresType: List<GenresType>?,
    val actors: Set<String>?,
    val publisherId: String?,
    val directorId: String?
)
