package com.folksdev.movie.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
data class Director @JvmOverloads constructor(
    @Id
    @Column(name = "director_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name: String,
    val lastName: String,

    @OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
    val movies: Set<Movie>? = HashSet()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Director

        if (id != other.id) return false
        if (name != other.name) return false
        if (lastName != other.lastName) return false
        if (movies != other.movies) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + movies.hashCode()
        return result
    }
}