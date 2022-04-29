package com.nick.codetest.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.nick.codetest.R
import com.nick.codetest.ext.safeLet
import kotlinx.android.synthetic.main.layout_empty_recyclerview.view.*

class EmptyRecyclerView: ConstraintLayout {

    constructor(context: Context) : super(context) {
        setupView(context, null, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        setupView(context, attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        setupView(context, attrs, defStyleAttr)
    }

    val recyclerView: RecyclerView
        get() {
            return rv
        }

    /**
     * the adapter of the recycleview
     */
    var adapter: RecyclerView.Adapter<*>? = null
        set(value) {
            // remove observer for old adapter
            field?.unregisterAdapterDataObserver(mAdapterDataObserver)

            field = value

            rv.adapter = value
            value?.registerAdapterDataObserver(mAdapterDataObserver)
        }

    /**
     * the layout manager of the recycler view
     */
    var layoutManager: RecyclerView.LayoutManager? = null
        set(value) {
            field = value

            rv.layoutManager = value
        }

    var itemDecoration: RecyclerView.ItemDecoration? = null
        set(value) {
            field = value

            value?.let {
                rv.addItemDecoration(it)
            }
        }

    /**
     * the inflated empty view
     */
    var emptyView: View? = null
        private set

    /**
     * the layout res of the empty view
     */
    @LayoutRes
    var emptyLayoutRes: Int? = null
        set(value) {
            field = value

            if (emptyView != null) {
                // the empty layout has been invalidated
                // todo needs more than one empty layout?
                return
            }

            value?.let {
                vs_empty.layoutResource = it
                emptyView = vs_empty.inflate()
            }
        }

    var listener: EmptyRecyclerViewListener? = null

    /**
     * the data observer, observing the data number of the adapter
     */
    private val mAdapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            checkIfEmpty()
        }
    }

    private fun setupView(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        LayoutInflater.from(context).inflate(R.layout.layout_empty_recyclerview, this, true)
        // do any custom attribute setting
        val ta: TypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.EmptyRecyclerView)
        try {
            val res = ta.getResourceId(R.styleable.EmptyRecyclerView_erv_empty_layout_src, 0)
            setEmptyLayoutRes(res)
        } finally {
            ta.recycle()
        }
    }

    private fun checkIfEmpty() {
        safeLet(emptyView, adapter) { v, a ->
            if (a.itemCount == 0) {
                v.isInvisible = !(listener?.shouldEmptyViewAppear() ?: true)
                rv.isInvisible = true
            } else {
                v.isInvisible = true
                rv.isInvisible = false
            }
        }
    }

    fun setEmptyLayoutRes(@LayoutRes res: Int) {
        if (emptyView != null) {
            // the empty layout has been invalidated
            // todo needs more than one empty layout?
            return
        }

        vs_empty.layoutResource = res
        try {
            emptyView = vs_empty.inflate()
        } catch (e: Exception) {
            // the res is not valid
            e.printStackTrace()
        }

        emptyView?.let {
            listener?.onEmptyLayoutInflated(it)
        }
    }

    interface EmptyRecyclerViewListener {
        /**
         * callback when the empty layout has been inflated, say you want a button clickable etc
         */
        fun onEmptyLayoutInflated(v: View) { }

        /**
         * condition if the empty view should be appear
         */
        fun shouldEmptyViewAppear(): Boolean {
            return true
        }
    }
}