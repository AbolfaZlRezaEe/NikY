package com.abproject.niky.view.views.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abproject.niky.R
import com.abproject.niky.model.dataclass.Comment
import com.abproject.niky.utils.other.Variables.SHOW_EVERY_THINGS

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private val comments = ArrayList<Comment>()

    /**
     * with this variable, you can pass SHOW_EVERY_THINGS or
     * SHOW_THREE_COMMENTS for changing show style in view.
     * SHOW_EVERY_THINGS -> show all comments in view.
     * SHOW_THREE_COMMENTS -> show only 3 comments in view.
     */
    var adapterFlag: Int = SHOW_EVERY_THINGS
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setData(
        comments: List<Comment>,
    ) {
        this.comments.clear()
        this.comments.addAll(comments)
        notifyDataSetChanged()
    }

    fun addComment(
        comment: Comment,
    ) {
        this.comments.add(0, comment)
        notifyItemInserted(0)
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentTitle: TextView = itemView.findViewById(R.id.commentTitleTextViewItem)
        val commentDate: TextView = itemView.findViewById(R.id.commentDateTextViewItem)
        val commentAuthor: TextView = itemView.findViewById(R.id.commentAuthorTextViewItem)
        val commentContent: TextView = itemView.findViewById(R.id.commentContentTextViewItem)

        fun bindComment(
            comment: Comment,
        ) {
            commentTitle.text = comment.title
            commentDate.text = comment.date
            commentAuthor.text = comment.author!!.email
            commentContent.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_comment,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }

    override fun getItemCount(): Int {
        return if (adapterFlag == SHOW_EVERY_THINGS && comments.size > 3)
            comments.size
        else if (comments.size < 3) {
            comments.size
        } else
            3
    }
}