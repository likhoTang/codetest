package com.nick.codetest.ui.bookmarkPage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.nick.codetest.R
import com.nick.codetest.application_preferences.ApplicationPreferences
import com.nick.codetest.base.BaseActivity
import com.nick.codetest.databinding.ActivityBookmarkBinding
import com.nick.codetest.ui.mainPage.MainRecyclerViewAdapter

class BookmarkActivity : BaseActivity<ActivityBookmarkBinding,BookmarkActivityViewModel>() {

    companion object {
        fun start(activity: Activity?) {
            activity?.let {
                it.startActivity(Intent(it, BookmarkActivity::class.java))
            }
        }
    }

    override val layout: Int
        get() = R.layout.activity_bookmark


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(layout)
        super.onCreate(savedInstanceState)

        initData()
        initView()
    }

    private fun initData() {
        val bookmarkList = ApplicationPreferences.getInstance(this).bookMarkList
        viewModel.ldList.value = bookmarkList
    }

    private fun initView(){
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivDeleteAll.setOnClickListener {
            ApplicationPreferences.getInstance(this).saveBookMarkList(arrayListOf())
        }
        val rvAdapter = MainRecyclerViewAdapter()
        binding.rvMain.recyclerView.itemAnimator?.let {
            (it as SimpleItemAnimator).supportsChangeAnimations = false
        }
        binding.rvMain.adapter = rvAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            val bookmarkList = ApplicationPreferences.getInstance(this).bookMarkList
            viewModel.ldList.value = bookmarkList

        }
        viewModel.ldList.observe(this) {
            rvAdapter.setDisplayData(it,this)
        }
    }

    override fun createViewModel(): BookmarkActivityViewModel {
        return ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(BookmarkActivityViewModel::class.java)
    }

    override fun bindingViewModel() {
        binding.viewModel = viewModel
    }
}