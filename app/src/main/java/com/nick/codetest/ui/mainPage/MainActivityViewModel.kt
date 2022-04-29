package com.nick.codetest.ui.mainPage

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.nick.codetest.base.BaseViewModel
import com.nick.codetest.entity.ResultItem
import com.nick.codetest.ext.disposedBy
import com.nick.codetest.retrofit.CodeTestService
import io.reactivex.rxjava3.core.Observable

class MainActivityViewModel:BaseViewModel() {
    var ldList:MutableLiveData<ArrayList<ResultItem>> = MediatorLiveData()


    fun getSearchAlbumResult(term:String): Observable<ArrayList<ResultItem>> {
        return CodeTestService.NO_AUTH_API.searchAlbum(term)
            .map {
                it.results
            }
    }

    fun searchAlbumAndUpdateLdList(term:String){
        CodeTestService.NO_AUTH_API.searchAlbum(term)
            .subscribe({
                ldList.value = it.results
//                Log.e("search result size","${it.results.size}")
//                Log.e("search result size","${it.results}")
            },Throwable::printStackTrace).disposedBy(disposeBag)
    }
}