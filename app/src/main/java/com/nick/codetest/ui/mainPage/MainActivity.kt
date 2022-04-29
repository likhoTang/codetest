package com.nick.codetest.ui.mainPage

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nick.codetest.R
import com.nick.codetest.base.BaseActivity
import com.nick.codetest.databinding.ActivityMainBinding
import com.nick.codetest.ui.bookmarkPage.BookmarkActivity

class MainActivity : BaseActivity<ActivityMainBinding,MainActivityViewModel>() {

    val rvAdapter = MainRecyclerViewAdapter()
    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(layout)
        super.onCreate(savedInstanceState)
        viewModel.mNavigator = this

        initSearchBar()
        initView()

    }


    private fun initView(){
        binding.ivBookmark.setOnClickListener{
            BookmarkActivity.start(this)
        }
        binding.rvMain.adapter = rvAdapter
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.ldSearchBarText.value?.let {
                viewModel.searchAlbumAndUpdateLdList(it)
            }

        }
        viewModel.ldList.observe(this) {
            rvAdapter.setDisplayData(it,this)
        }

    }

    private fun initSearchBar(){
        binding.searchBar.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                hideSoftKeyBoard()
                viewModel.ldSearchBarText.value?.let {
                    viewModel.searchAlbumAndUpdateLdList(it)
                }
            }
            false
        }
        binding.searchBar.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideSoftKeyBoard()
                viewModel.ldSearchBarText.value?.let {
                    viewModel.searchAlbumAndUpdateLdList(it)
                }
                return@setOnKeyListener true
            }
            false
        }
    }

    override fun onResume() {
        viewModel.ldList.value?.let {
            rvAdapter.setDisplayData(it,this)
        }
        super.onResume()
    }


    override fun createViewModel(): MainActivityViewModel {
        return ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(MainActivityViewModel::class.java)
    }

    override fun bindingViewModel() {
        binding.viewModel = viewModel
    }
}