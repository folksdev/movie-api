package com.folksdev.movie.model

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import javax.persistence.*

@Entity
data class Actor @JvmOverloads constructor(
    @Id
    @Column(name = "actor_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name: String,
    val dateOfBirth: LocalDate,
    val gender: Gender,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "actor_movies",
        joinColumns = [JoinColumn(name = "actor_id", referencedColumnName = "actor_id")],
        inverseJoinColumns = [JoinColumn(name = "movie_id", referencedColumnName = "movie_id")]
    )
    val movies: Set<Movie>? = HashSet()

) {

}

enum class Gender {
    MALE, FEMALE, UNKNOWN
}

