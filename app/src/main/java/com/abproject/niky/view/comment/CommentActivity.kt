package com.abproject.niky.view.comment

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityCommentBinding
import com.abproject.niky.model.model.Comment
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
    }

    private fun setupUi() {
        binding.addCommentFab.setOnClickListener {
            //this method will be developing later...
        }
        binding.toolbarCommentList.onBackButtonClickListener = View.OnClickListener {
            this.finish()
        }
    }

    private fun listeningToObservers() {
        commentViewModel.getAllComments.observe(this) { response ->
            initializeRecyclerView(response)
        }

        commentViewModel.addCommentStatus.observe(this) { comment ->

        }

        commentViewModel.progressbarStatus.observe(this) { show ->
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
}