package com.nick.codetest.ui.mainPage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nick.codetest.R
import com.nick.codetest.application_preferences.ApplicationPreferences
import com.nick.codetest.databinding.LayoutListItemAlbumBinding
import com.nick.codetest.entity.ResultItem

class MainRecyclerViewAdapter: RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>() {
    private var displayData = ArrayList<ResultItem>()
    fun setDisplayData(data:ArrayList<ResultItem>,context:Context){
        val bufferData = ApplicationPreferences.getInstance(context).bookMarkList
        val ids = bufferData.map { it.collectionId }
        data.forEach {
            it.isFavorite = it.collectionId in ids
        }
        this.displayData = data
        notifyDataSetChanged()
    }
    class ViewHolder(val binding: LayoutListItemAlbumBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutListItemAlbumBinding>(
            LayoutInflater.from(parent.context), R.layout.layout_list_item_album,
            parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.resultItemAlbum = displayData[position]
        holder.binding.btnAddToCollection.setOnClickListener { it ->
            displayData[position].isFavorite = !displayData[position].isFavorite

            val bufferData = ApplicationPreferences.getInstance(it.context).bookMarkList

            var itemDelete: ResultItem? = null
            bufferData.forEach {
                if (displayData[position].collectionId == it.collectionId){
                   itemDelete = it
                }
            }
            if (itemDelete != null){
                bufferData.remove(itemDelete)
            }else{
                bufferData.add(displayData[position])
            }
            ApplicationPreferences.getInstance(it.context).saveBookMarkList(bufferData)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return this.displayData.size
    }
}