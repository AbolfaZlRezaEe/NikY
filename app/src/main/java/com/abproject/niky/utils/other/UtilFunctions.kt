package com.abproject.niky.utils.other

import android.content.Context
import android.content.res.Resources
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics

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

}