package tik.prometheus.mobile.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.res.use


open class Utils {
    companion object{
        @ColorInt
        @SuppressLint("Recycle")
        fun Context.themeColor(themeAttrId: Int): Int {
            return obtainStyledAttributes(
                intArrayOf(themeAttrId)
            ).use {
                it.getColor(0, Color.MAGENTA)
            }
        }
    }
}