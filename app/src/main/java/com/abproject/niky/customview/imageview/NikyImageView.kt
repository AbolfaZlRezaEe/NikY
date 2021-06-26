package com.abproject.niky.customview.imageview

import android.content.Context
import android.util.AttributeSet
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.view.SimpleDraweeView

/**
 * NikyImageView that a Custom view for use in
 * application instead of normal ImageView
 */
class NikyImageView : SimpleDraweeView {

    constructor(context: Context?, hierarchy: GenericDraweeHierarchy?) : super(context, hierarchy)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context,
        attrs,
        defStyle)

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes)
}