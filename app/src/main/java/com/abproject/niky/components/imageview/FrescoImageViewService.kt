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
        if (imageView is SimpleDraweeView) {
            imageView.setImageURI(imageUrl)
        } else {
            //throwing exception if this imageView doesn't instance of SimpleDraweeView!
            throw IllegalStateException("debug (FrescoImageViewService)-> ImageView must be instance of SimpleDraweeView! ")
        }
    }

}