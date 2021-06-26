package com.abproject.niky.model.datasource.comment

import com.abproject.niky.model.model.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun getComments(
        productId: Int,
    ): Single<List<Comment>>

    fun addComment(
        comment: Comment,
    ): Single<Comment>
}