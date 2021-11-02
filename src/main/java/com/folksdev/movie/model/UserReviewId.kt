package com.folksdev.movie.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class UserReviewId(
    @Column(name = "user_id", insertable = false, updatable = false)
    val userId: String,
    @Column(name = "review_id", insertable = false, updatable = false)
    val reviewId: String
): Serializable{

}
