package com.folksdev.movie.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
data class Movie @JvmOverloads constructor(
    @Id //PRIMARY KEY
    @Column(name = "movie_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val title: String,
    val description: String,
    val imdbUrl: String,
    val duration: Int,
    val featuredYear: Int,
    @field:ElementCollection(fetch = FetchType.EAGER) // ONE TO MANY iliski tablosu
    val genresTypes: List<GenresType>,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "actor_movies",
        joinColumns = [JoinColumn(name = "movie_id", referencedColumnName = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id", referencedColumnName = "actor_id")]
    )
    val actors: Set<Actor>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id", referencedColumnName = "director_id") // Movie = Owner(fk)
    val director: Director,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "pub_id", referencedColumnName = "publisher_id")
    val publisher: Publisher
)

enum class GenresType {
    COMEDY, DRAMA, HORROR, ROMANCE, FANTASY, ACTION, MYSTERY, SCI_FI, THRILLER
}