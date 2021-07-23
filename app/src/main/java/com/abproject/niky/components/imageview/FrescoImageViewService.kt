package com.abproject.niky.components.imageview

import com.abproject.niky.customview.imageview.NikyImageView
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.IllegalStateException

/**
 * this is the component class which used for
 * loading image into application. this class
 * implement ImageLoadingService for implement
 * contract.
 */
class FrescoImageViewService : ImageLoadingService {

    /**
     * loadImage function takes an imageView so is should be
     * instance of SimpleDraweeView, and imageUrl for loading
     * image with fresco.
     */
    override fun loadImage(imageView: NikyImageView, imageUrl: String) {
        imageView.setImageURI(imageUrl)
    }

}