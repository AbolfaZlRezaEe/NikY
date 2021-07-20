package com.abproject.niky.model.datasource.comment

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.dataclass.Comment
import com.abproject.niky.utils.other.Variables.JSON_CONTENT_KEY
import com.abproject.niky.utils.other.Variables.JSON_PRODUCT_ID_KEY
import com.abproject.niky.utils.other.Variables.JSON_TITLE_KEY
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(
    private val apiService: NikyApiService,
) : CommentDataSource {

    override fun getComments(productId: Int): Single<List<Comment>> {
        return apiService.getComments(productId)
    }

    override fun addComment(comment: Comment): Single<Comment> {
        return apiService.addComment(JsonObject().apply {
            addProperty(JSON_TITLE_KEY, comment.title)
            addProperty(JSON_CONTENT_KEY, comment.content)
            addProperty(JSON_PRODUCT_ID_KEY, comment.id)
        })
    }
}