package com.folksdev.movie.dto

import com.folksdev.movie.model.GenresType

class UpdateMovieRequest @JvmOverloads constructor(
    description: String?,
    imdbUrl: String?,
    duration: Int?,
    featuredYear: Int?,
    genresType: List<GenresType>?,
    actorIds: List<String>?,
    publisherId: String?,
    directorId: String?
) : BaseMovieRequest(description, imdbUrl, duration, featuredYear, genresType, actorIds, publisherId, directorId)
