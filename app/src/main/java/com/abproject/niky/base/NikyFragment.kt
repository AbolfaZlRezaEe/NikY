package com.abproject.niky.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.abproject.niky.utils.internetconnetion.ConnectionLiveData
import com.abproject.niky.utils.other.UtilFunctions
import org.greenrobot.eventbus.EventBus

abstract class NikyFragment : Fragment(), NikyView {

    lateinit var connectionLiveData: ConnectionLiveData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize connection live data
        connectionLiveData = ConnectionLiveData(requireContext())
    }

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