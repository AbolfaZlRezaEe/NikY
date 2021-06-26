package com.abproject.niky.model.datasource.comment

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.model.Comment
import io.reactivex.Single
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(
    private val apiService: NikyApiService,
) : CommentDataSource {

    override fun getComments(productId: Int): Single<List<Comment>> {
        return apiService.getComments(productId)
    }

    override fun addComment(comment: Comment): Single<Comment> {
        TODO("Not yet implemented")
    }
}