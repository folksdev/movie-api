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

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    val movies: Set<Movie>? = HashSet()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Actor

        if (id != other.id) return false
        if (name != other.name) return false
        if (dateOfBirth != other.dateOfBirth) return false
        if (gender != other.gender) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + dateOfBirth.hashCode()
        result = 31 * result + gender.hashCode()
        return result
    }
}



enum class Gender {
    MALE, FEMALE, UNKNOWN
}

