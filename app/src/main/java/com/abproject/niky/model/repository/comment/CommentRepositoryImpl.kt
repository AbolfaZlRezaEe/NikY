package com.abproject.niky.model.repository.comment

import com.abproject.niky.model.datasource.comment.CommentDataSource
import com.abproject.niky.model.model.Comment
import io.reactivex.Single
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentDataSource: CommentDataSource,
) : CommentRepository {

    override fun getComments(productId: Int): Single<List<Comment>> {
        return commentDataSource.getComments(productId)
    }

    override fun addComment(comment: Comment): Single<Comment> {
        return commentDataSource.addComment(comment)
    }
}