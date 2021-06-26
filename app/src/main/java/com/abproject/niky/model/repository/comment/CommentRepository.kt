package com.abproject.niky.model.repository.comment

import com.abproject.niky.model.model.Comment
import io.reactivex.Single

interface CommentRepository {

    fun getComments(
        productId: Int,
    ): Single<List<Comment>>

    fun addComment(
        comment: Comment,
    ): Single<Comment>
}