package com.abproject.niky.model.model

data class Comment(
    val author: Author,
    val content: String,
    val date: String,
    val id: Int,
    val title: String
)