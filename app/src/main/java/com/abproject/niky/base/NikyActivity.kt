package com.abproject.niky.base

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import org.greenrobot.eventbus.EventBus

abstract class NikyActivity : AppCompatActivity(), NikyView {

    /**
     * this function searching in the viewGroups in our layout
     * and find a layout is an instance of Coordinator layout.
     * so, if doesn't exist, thrown an illegalStateException.
     */
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("debug (NikyActivity)-> RootView must be instance of CoordinatorLayout")
            } else
                return viewGroup
        }

    override val viewContext: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //connect eventbus to the view
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        //disconnect eventbus from the view
        EventBus.getDefault().unregister(this)
    }
}