package com.abproject.niky.components.imageview

import com.abproject.niky.customview.NikyImageView

interface ImageLoadingService {

    fun loadImage(
        imageView: NikyImageView,
        imageUrl:String
    )
}