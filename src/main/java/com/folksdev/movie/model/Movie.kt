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

    @ManyToMany(mappedBy = "movies", fetch = FetchType.LAZY)
    val actors: Set<Actor>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id", referencedColumnName = "director_id") // Movie = Owner(fk)
    val director: Director,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "pub_id", referencedColumnName = "publisher_id")
    val publisher: Publisher
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != null && id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (imdbUrl != other.imdbUrl) return false
        if (duration != other.duration) return false
        if (featuredYear != other.featuredYear) return false
        if (genresTypes != other.genresTypes) return false
        if (actors != other.actors) return false
        if (director != other.director) return false
        if (publisher != other.publisher) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + imdbUrl.hashCode()
        result = 31 * result + duration
        result = 31 * result + featuredYear
        result = 31 * result + genresTypes.hashCode()
        result = 31 * result + actors.hashCode()
        result = 31 * result + director.id.hashCode()
        result = 31 * result + publisher.hashCode()
        return result
    }
}

enum class GenresType {
    COMEDY, DRAMA, HORROR, ROMANCE, FANTASY, ACTION, MYSTERY, SCI_FI, THRILLER
}