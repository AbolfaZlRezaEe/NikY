package com.abproject.niky.utils.other

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import androidx.browser.customtabs.CustomTabsIntent
import com.abproject.niky.R
import com.abproject.niky.utils.exceptionhandler.ExceptionType
import com.abproject.niky.utils.exceptionhandler.NikyExceptionMapper
import com.abproject.niky.utils.exceptionhandler.exceptions.InternetException
import org.greenrobot.eventbus.EventBus

/**
 * this class contain all of utils method that we need in project.
 */
object UtilFunctions {

    //Converting Dp to Pixel by use Context. return -> Float
    fun convertDpToPixel(dp: Float, context: Context?): Float {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            val metrics = Resources.getSystem().displayMetrics
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

    //Adds "تومان" to the end of the text. return -> SpannableString
    fun formatPrice(
        price: Number,
        //for changing size of "تومان", change the number of unitRelativeSizeFactor!
        unitRelativeSizeFactor: Float = 0.7f,
    ): SpannableString {
        val currencyLabel = "تومان"
        val spannableString = SpannableString("$price $currencyLabel")
        spannableString.setSpan(
            RelativeSizeSpan(unitRelativeSizeFactor),
            spannableString.indexOf(currencyLabel),
            spannableString.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    //this function take a url and context and then open a browser tab
    fun openUrlInCustomTab(context: Context, url: String) {
        try {
            val uri = Uri.parse(url)
            val intentBuilder = CustomTabsIntent.Builder()
            intentBuilder.setStartAnimations(context,
                android.R.anim.fade_in,
                android.R.anim.fade_out)
            intentBuilder.setExitAnimations(context,
                android.R.anim.fade_in,
                android.R.anim.fade_out)
            val customTabsIntent = intentBuilder.build()
            customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            customTabsIntent.launchUrl(context, uri)
        } catch (e: Exception) {
            EventBus.getDefault().post(NikyExceptionMapper.map(InternetException(
                exceptionType = ExceptionType.SIMPLE,
                resourceStringMessage = R.string.snackbarExceptionMessage
            )))
        }
    }
}