package com.abproject.niky.view.views.addcomment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.abproject.niky.base.NikyViewModel
import com.abproject.niky.model.dataclass.Comment
import com.abproject.niky.model.repository.comment.CommentRepository
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_PRODUCT_ID_DATA
import com.abproject.niky.utils.other.asyncNetworkRequest
import com.abproject.niky.utils.rxjava.NikySingleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCommentViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val commentRepository: CommentRepository,
) : NikyViewModel() {

    private val _commentStatusLiveData = MutableLiveData<Comment>()

    val commentStatusLiveData: LiveData<Comment> get() = _commentStatusLiveData

    private fun getProductId(): Int {
        return savedStateHandle.get<Int>(EXTRA_KEY_PRODUCT_ID_DATA)!!
    }

    fun addComment(
        title: String,
        content: String,
    ) {
        _progressbarStatusLiveData.value = true
        val comment = Comment(
            title = title,
            content = content,
            id = getProductId(),
        )
        commentRepository.addComment(comment)
            .asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
            .subscribe(object : NikySingleObserver<Comment>(compositeDisposable) {
                override fun onSuccess(commentResponse: Comment) {
                    _commentStatusLiveData.value = commentResponse
                }
            })
    }

}