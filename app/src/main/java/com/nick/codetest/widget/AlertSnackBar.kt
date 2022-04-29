package com.nick.codetest.widget

import android.annotation.SuppressLint
import android.app.ActionBar
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.nick.codetest.R
import com.nick.codetest.util.Util


class AlertSnackBar private constructor() {

    companion object {

        fun show(
            anchor: View,
            msg: String,
            @DrawableRes icon: Int? = null,
            onDismiss: (() -> Unit)? = null
        ) {
            val context = anchor.context
            Snackbar.make(anchor, msg, Snackbar.LENGTH_LONG)
                .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        onDismiss?.invoke()
                    }
                }).apply {
                    setup(this, icon)
                    duration = 1000
                    show()
                }
        }

        fun show2(
            anchor: View,
            msg: String,
            @DrawableRes icon: Int? = null,
            onDismiss: (() -> Unit)? = null
        ) {
            val context = anchor.context
            Snackbar.make(anchor, msg, Snackbar.LENGTH_LONG)
                .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        onDismiss?.invoke()
                    }
                }).apply {
                    setup(this, icon)
                    duration = 1000
                    show()
                }
        }

        @SuppressLint("WrongConstant", "RestrictedApi")
        private fun setup(s: Snackbar, @DrawableRes icon: Int?) {
            s.apply {
                setTextColor(
                    ResourcesCompat.getColor(
                        context.resources,
                        R.color.white,
                        null
                    )
                )


                animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

                val v = view
                v.background = ContextCompat.getDrawable(context, R.drawable.snackbar_bg)

                if (v is Snackbar.SnackbarLayout) {
                    for (i in 0 until v.childCount) {
                        v[i].apply {
                            val layout = v[i]

                            if (layout is SnackbarContentLayout) {
                                layout.messageView?.let {
//                                    layout.setBackgroundColor(
//                                        ResourcesCompat.getColor(
//                                            context.resources,
//                                            R.color.black_opacity,
//                                            null
//                                        )
//                                    )

                                    icon?.let { ic ->
                                        val ic = ContextCompat.getDrawable(
                                            context,
                                            ic
                                        )
                                        it.setCompoundDrawablesWithIntrinsicBounds(
                                            ic,
                                            null,
                                            null,
                                            null
                                        )
                                        it.compoundDrawablePadding = Util.dp2px(context, 10)
                                    }
                                }
                            }
                        }
                    }
                }

                val params = v.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.CENTER_HORIZONTAL
                params.topMargin= Util.dp2px(context, 238)
                params.width = if (icon != null) {
                    Util.dp2px(context, 145)
                } else {
                    ActionBar.LayoutParams.WRAP_CONTENT
                }
//                params.marginStart = Util.dp2px(context, 75)
//                params.marginEnd = Util.dp2px(context, 75)
                v.layoutParams = params
            }
        }

    }

}