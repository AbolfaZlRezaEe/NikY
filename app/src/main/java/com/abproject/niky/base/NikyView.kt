package com.abproject.niky.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.abproject.niky.R
import com.google.android.material.snackbar.Snackbar

interface NikyView {

    val rootView: CoordinatorLayout?
    val viewContext: Context?

    /**
     * this method show progress bar if mustsShow is true.
     * also loadingView layout is lazy inflate, you don't need
     * add loadingView to all layout in application, just load
     * when needed.
     */
    fun showProgressbar(
        mustShow: Boolean,
    ) {
        rootView?.let { coordinatorLayout ->
            viewContext?.let { context ->
                var loadingView = coordinatorLayout.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow) {
                    loadingView = LayoutInflater.from(context)
                        .inflate(R.layout.loading_view,
                            coordinatorLayout,
                            false
                        )
                    coordinatorLayout.addView(loadingView)
                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }

    /**
     * showSnackBar is a custom Function I made for views
     * this function working in Activities and Fragments.
     * Note: this Function only works on views that have Coordinator Layout!
     */
    fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }

}