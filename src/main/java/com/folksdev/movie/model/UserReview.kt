package com.folksdev.movie.model

import java.time.LocalDateTime
import javax.persistence.*

/*
    | user_id      |   review_id   |
    |  PK          |    PK         |
                HASH
    user_review_id, user_id, review_id


         USER                REVIEW
               USER_REVIEW
              (userReviewId)
 */

@Entity
//@IdClass(UserReviewId::class)
data class UserReview(
    /*@Id
    //@Column(name = "user_id", insertable = false, updatable = false)
    val userId: String,
    @Id
    //@Column(name = "review_id", insertable = false, updatable = false)
    val reviewId: String,*/
    @EmbeddedId
    val userReviewId: UserReviewId,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    val user: User,
    @ManyToOne(cascade = [CascadeType.ALL],  fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    val review: Review,
    val date: LocalDateTime
) {}
