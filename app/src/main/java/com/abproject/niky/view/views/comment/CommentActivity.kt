package com.abproject.niky.view.views.comment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityCommentBinding
import com.abproject.niky.model.dataclass.Comment
import com.abproject.niky.utils.other.Variables
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_COMMENT
import com.abproject.niky.view.views.addcomment.AddCommentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentActivity : NikyActivity() {

    private val commentViewModel: CommentViewModel by viewModels()
    private lateinit var binding: ActivityCommentBinding
    private val commentAdapter = CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listeningToObservers()
        setupUi()
        commentViewModel.getAllComments()
    }

    private fun setupUi() {
        binding.addCommentFloatingActionButton.setOnClickListener {
            startActivityForResult(Intent(this, AddCommentActivity::class.java).apply {
                putExtra(Variables.EXTRA_KEY_PRODUCT_ID_DATA,
                    commentViewModel.productIdLiveData.value)
            }, Variables.REQUEST_COMMENT_KEY)
        }
        binding.toolbarCommentList.onBackButtonClickListener = View.OnClickListener {
            this.finish()
        }
    }

    private fun listeningToObservers() {
        commentViewModel.getAllComments.observe(this) { response ->
            initializeRecyclerView(response)
        }

        commentViewModel.progressbarStatusLiveData.observe(this) { show ->
            showProgressbar(show)
        }
    }

    private fun initializeRecyclerView(
        comments: List<Comment>,
    ) {
        binding.commentsRecyclerView.layoutManager = LinearLayoutManager(
            this,
            VERTICAL,
            false
        )
        commentAdapter.setData(comments)
        binding.commentsRecyclerView.adapter = commentAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (
            requestCode == Variables.REQUEST_COMMENT_KEY
            && resultCode == Activity.RESULT_OK
            && data != null
        ) {
            val comment = data.getParcelableExtra<Comment>(EXTRA_KEY_COMMENT)!!
            commentAdapter.addComment(comment)
            binding.commentsRecyclerView.smoothScrollToPosition(0)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}