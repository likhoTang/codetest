package com.nick.codetest.ui.bookmarkPage

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.nick.codetest.CodeTestApplication.Companion.BOOKMARK_SUBJECT
import com.nick.codetest.base.BaseViewModel
import com.nick.codetest.entity.ResultItem
import com.nick.codetest.ext.disposeBy

class BookmarkActivityViewModel:BaseViewModel() {
    var ldList: MutableLiveData<ArrayList<ResultItem>> = MediatorLiveData()

    init {
        BOOKMARK_SUBJECT.subscribe {
            ldList.value = it
        }.disposeBy(disposeBag)
    }
}