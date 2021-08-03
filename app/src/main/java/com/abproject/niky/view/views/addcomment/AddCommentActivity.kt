package com.abproject.niky.view.views.addcomment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.abproject.niky.R
import com.abproject.niky.base.NikyActivity
import com.abproject.niky.databinding.ActivityAddCommentBinding
import com.abproject.niky.utils.other.Variables.EXTRA_KEY_COMMENT
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class AddCommentActivity : NikyActivity() {

    private lateinit var binding: ActivityAddCommentBinding
    private val addCommentViewModel: AddCommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        listeningToTheObservers()
    }

    private fun initializeViews() {
        binding.toolbarAddComment.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        binding.addCommentFloatingActionBottom.setOnClickListener {
            if (validationEditTexts()) {
                addCommentViewModel.addComment(
                    title = binding.commentTitleEditTextAddComment.text.toString(),
                    content = binding.commentContentEditTextAddComment.text.toString()
                )
            } else
                setErrorForEditTexts()
        }
    }

    private fun listeningToTheObservers() {
        addCommentViewModel.commentStatusLiveData.observe(this) { comment ->
            if (comment != null) {
                binding.addCommentFloatingActionBottom.visibility = View.GONE
                showSnackBar(getString(R.string.addingCommentSuccessfully))
                Timer("finishAddCommentActivity").schedule(3000) {
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra(EXTRA_KEY_COMMENT, comment)
                    })
                    finish()
                }
            }else
                binding.addCommentFloatingActionBottom.visibility = View.VISIBLE
        }

        addCommentViewModel.progressbarStatusLiveData.observe(this) { status ->
            showProgressbar(status)
        }
    }

    private fun validationEditTexts(): Boolean {
        return binding.commentTitleEditTextAddComment.text.isNotEmpty()
                && binding.commentContentEditTextAddComment.text.isNotEmpty()
    }

    private fun setErrorForEditTexts() {
        if (binding.commentTitleEditTextAddComment.text.isNullOrEmpty()) {
            binding.commentTitleEditTextAddComment.error = getString(R.string.commentTitleError)
        }
        if (binding.commentContentEditTextAddComment.text.isNullOrEmpty()) {
            binding.commentContentEditTextAddComment.error = getString(R.string.commentContentError)
        }
    }
}