package com.abproject.niky.base

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.abproject.niky.R
import com.abproject.niky.utils.exceptionhandler.ExceptionType
import com.abproject.niky.model.dataclass.NikyException
import com.abproject.niky.view.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.concurrent.schedule

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
        mustBackgroundTransparent: Boolean = false,
    ) {
        rootView?.let { coordinatorLayout ->
            viewContext?.let { context ->
                var loadingView = coordinatorLayout.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow) {
                    loadingView = LayoutInflater.from(context)
                        .inflate(R.layout.view_loading,
                            coordinatorLayout,
                            false
                        )
                    coordinatorLayout.addView(loadingView)
                }
                if (mustBackgroundTransparent)
                    loadingView?.setBackgroundResource(android.R.color.transparent)
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }

    /**
     * showSnackBar is a custom Function I made for views
     * this function working in Activities and Fragments.
     * Note: this Function only works on views that have Coordinator Layout!
     */
    fun showSnackBar(
        message: String,
        duration: Int = Snackbar.LENGTH_SHORT,
    ) {
        rootView?.let { coordinatorLayout ->
            Snackbar.make(coordinatorLayout, message, duration).show()
        }
    }

    /**
     * showToastMessage is a custom Function I made for views
     * this function working in Activities and Fragments.
     * Note: this Function only works on views that have Coordinator Layout!
     */
    fun showToastMessage(
        message: String,
        duration: Int = Toast.LENGTH_SHORT,
    ) {
        rootView?.let { coordinatorLayout ->
            viewContext?.let { context ->
                Toast.makeText(context, message, duration).show()
            }
        }
    }

    /**
     * contain all errors publish from event bus.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(
        nikyException: NikyException,
    ) {
        viewContext?.let { context ->
            when (nikyException.exceptionType) {
                ExceptionType.SIMPLE -> {
                    showSnackBar(context.getString(nikyException.resourceStringMessage)
                    )
                }
                ExceptionType.AUTH -> {
                    showSnackBar(
                        nikyException.exceptionMessage!!
                    )
                    Timer("startAuthActivity", false).schedule(3000) {
                        context.startActivity(Intent(context, AuthActivity::class.java))
                    }
                }
                ExceptionType.TIMEOUT -> {
                    showSnackBar(
                        context.getString(nikyException.resourceStringMessage))
                }
                else -> {
                    showSnackBar(
                        nikyException.exceptionMessage
                            ?: context.getString(nikyException.resourceStringMessage)
                    )
                }
            }
        }
    }

}