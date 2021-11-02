package com.folksdev.movie.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
data class Review(
    @Id //PRIMARY KEY
    @Column(name = "review_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",
    val review: String,
    @OneToMany(mappedBy = "review", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val userReviews: List<UserReview>
)
