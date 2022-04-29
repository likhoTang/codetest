package com.nick.codetest.databinding_adapter

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nick.codetest.R

class ViewDataBindingAdapter {

    companion object{
        @JvmStatic
        @BindingAdapter("app:setFavJobIcon")
        fun setFavJobIcon(iv: ImageView, fav:Boolean?){
            fav?.let {
                if (it){
                    iv.setImageResource(R.drawable.ic_collect_icon_selected)
                }else{
                    iv.setImageResource(R.drawable.ic_collect_icon)
                }
            }

        }

        @SuppressLint("SetTextI18n")
        @JvmStatic
        @BindingAdapter(value = ["bind:currency", "bind:collectionPrice"], requireAll = false)
        fun setPriceText(tv:TextView,currency:String?,collectionPrice:String?){
            tv.text = "${currency} ${collectionPrice}"
        }



        @JvmStatic
        @BindingAdapter("app:ivSetImage")
        fun ivSetImage(iv:ImageView,url:String?){
            url?.let {

                Glide.with(iv.context)
                    .load(url)
                    .error(R.drawable.ic_baseline_downloading_24)
                    .fitCenter()
                    .into(iv)
            }

        }


    }
}