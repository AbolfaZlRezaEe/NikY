package com.abproject.niky.base

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment

abstract class NikyFragment : Fragment(), NikyView {

    override val viewContext: Context?
        get() = context

    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout
}