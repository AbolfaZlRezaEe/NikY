package com.abproject.niky.model.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val author: Author? = null,
    val content: String,
    val date: String? = null,
    val id: Int,
    val title: String,
) : Parcelable