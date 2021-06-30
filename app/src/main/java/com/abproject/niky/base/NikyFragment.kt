package com.abproject.niky.base

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus

abstract class NikyFragment : Fragment(), NikyView {

    override val viewContext: Context?
        get() = context

    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override fun onStart() {
        super.onStart()
        //connect eventbus to the view
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        //disconnect eventbus to the view
        EventBus.getDefault().unregister(this)
    }
}