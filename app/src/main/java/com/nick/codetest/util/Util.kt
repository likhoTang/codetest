package com.nick.codetest.util

import android.content.Context
import android.util.TypedValue

class Util {
    companion object {
        fun dp2px(context: Context, dp: Int): Int {
            return dp2px(context, dp * 1.0f).toInt()
        }

        fun dp2px(context: Context, dp: Float): Float {
            return convertDpPx(dp, TypedValue.COMPLEX_UNIT_DIP, context)
        }

        private fun convertDpPx(value: Float, unit: Int, context: Context): Float {
            return TypedValue.applyDimension(unit, value, context.resources.displayMetrics)
        }
    }
}

fun String.toUrlArray(): List<String> {
    return this.split(";").map { url -> url.endWithSlash() }
}

fun String.endWithSlash(): String {
    return if (this.endsWith("/")) {
        this
    } else {
        "$this/"
    }
}