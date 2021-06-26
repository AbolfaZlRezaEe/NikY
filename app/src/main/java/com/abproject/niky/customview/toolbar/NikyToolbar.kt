package com.abproject.niky.customview.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.abproject.niky.R

/**
 * this is a custom view for Niky toolbar.
 */
class NikyToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    //set onClick listener for backImageViewItemToolbar
    var onBackButtonClickListener: View.OnClickListener? = null
        set(value) {
            field = value
            findViewById<ImageView>(R.id.backImageViewItemToolbar).setOnClickListener(
                onBackButtonClickListener)
        }

    init {
        //inflating layout.
        inflate(context, R.layout.view_toolbar, this)

        setupTitleTextView(context, attrs)
    }

    /**
     * this method take context and AttributeSet in input and
     * check the niky_toolbar_text attribute.
     * if
     * niky_toolbar_text is a attribute that take a string for
     * set in toolbar title. it niky_toolbar_text is not empty,
     * that text set in toolbarTextViewItemToolbar.
     */
    private fun setupTitleTextView(
        context: Context,
        attrs: AttributeSet?,
    ) {
        attrs?.let { attributeSet ->
            val getCustomView =
                context.obtainStyledAttributes(attributeSet, R.styleable.NikyToolbar)

            val title = getCustomView.getString(R.styleable.NikyToolbar_niky_toolbar_text)

            if (!title.isNullOrEmpty()) {
                findViewById<TextView>(R.id.toolbarTextViewItemToolbar).text = title
            }

            getCustomView.recycle()
        }
    }
}