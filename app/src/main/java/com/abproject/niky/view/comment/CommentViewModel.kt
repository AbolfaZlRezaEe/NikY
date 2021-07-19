package com.abproject.niky.view.comment

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
class CommentViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val savedStateHandle: SavedStateHandle,
) : NikyViewModel() {

    private val _getAllComments = MutableLiveData<List<Comment>>()
    private val _addCommentStatus = MutableLiveData<Comment>()

    val getAllComments: LiveData<List<Comment>> get() = _getAllComments
    val addCommentStatus: LiveData<Comment> get() = _addCommentStatus


    /**
     * this method using view savedStateHandle for take
     * extra variable that ProductDetailActivity passed.
     * then give product id to getAllComments functions
     * for sending request to the server.
     */
    private fun getProductId(): Int {
        return savedStateHandle.get<Int>(EXTRA_KEY_PRODUCT_ID_DATA)!!
    }

    fun getAllComments() {
        _progressbarStatusLiveData.postValue(true)
        commentRepository.getComments(getProductId())
            .asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
            .subscribe(object : NikySingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(response: List<Comment>) {
                    _getAllComments.postValue(response)
                }
            })

    }

    fun addComments(
        comment: Comment,
    ) {
        _progressbarStatusLiveData.postValue(true)
        commentRepository.addComment(comment)
            .asyncNetworkRequest()
            .doFinally { _progressbarStatusLiveData.postValue(false) }
            .subscribe(object : NikySingleObserver<Comment>(compositeDisposable) {
                override fun onSuccess(response: Comment) {
                    _addCommentStatus.postValue(response)
                }
            })

    }

}