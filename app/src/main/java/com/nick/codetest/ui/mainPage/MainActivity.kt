package com.nick.codetest.ui.mainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.nick.codetest.R
import com.nick.codetest.base.BaseActivity
import com.nick.codetest.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding,MainActivityViewModel>() {
    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(layout)
        super.onCreate(savedInstanceState)

        binding.testText.setOnClickListener {
            viewModel.searchAlbumAndUpdateLdList("aaa")
        }
    }

    override fun createViewModel(): MainActivityViewModel {
        return ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(MainActivityViewModel::class.java)
    }

    override fun bindingViewModel() {
        binding.viewModel = viewModel
    }
}