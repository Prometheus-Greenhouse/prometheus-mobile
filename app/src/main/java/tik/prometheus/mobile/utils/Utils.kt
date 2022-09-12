package tik.prometheus.mobile.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import java.lang.reflect.Modifier
import java.util.*


object Utils {
//    @ColorInt
//    @SuppressLint("Recycle")
//    fun Context.themeColor(themeAttrId: Int): Int {
//        return obtainStyledAttributes(
//            intArrayOf(themeAttrId)
//        ).use {
//            it.getColor(0, Color.MAGENTA)
//        }
//    }
    fun reflectionToString(obj: Any): String {
        val s = LinkedList<String>()
        var clazz: Class<in Any>? = obj.javaClass
        while (clazz != null) {
            for (prop in clazz.declaredFields.filterNot { Modifier.isStatic(it.modifiers) }) {
                prop.isAccessible = true
                s += "${prop.name}=" + prop.get(obj)?.toString()?.trim()
            }
            clazz = clazz.superclass
        }
        return "${obj.javaClass.simpleName}=[${s.joinToString(", ")}]"
    }
}
@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(themeAttrId: Int): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}
