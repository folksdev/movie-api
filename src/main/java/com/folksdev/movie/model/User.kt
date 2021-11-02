package com.folksdev.movie.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "movie_user")
data class User(
    @Id //PRIMARY KEY
    @Column(name = "user_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val name: String,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val userReviews: List<UserReview>

)
