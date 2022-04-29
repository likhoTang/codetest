package com.nick.codetest.ui.mainPage

import android.annotation.SuppressLint
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.nick.codetest.base.BaseViewModel
import com.nick.codetest.entity.ResultItem
import com.nick.codetest.ext.disposedBy
import com.nick.codetest.retrofit.CodeTestService
import io.reactivex.rxjava3.core.Observable

class MainActivityViewModel():BaseViewModel() {
    @SuppressLint("StaticFieldLeak")
    var mNavigator: MainActivity? = null
    var ldList:MutableLiveData<ArrayList<ResultItem>> = MediatorLiveData()
    var ldSearchBarText: MutableLiveData<String> = MediatorLiveData()

    fun searchAlbumAndUpdateLdList(term:String){
        mNavigator?.let{
            mNavigator!!.showLoadingDialog("Loading...")
        }

        CodeTestService.NO_AUTH_API.searchAlbum(term)
            .doFinally {
                mNavigator?.let{
                    mNavigator!!.dismissLoadingDialog()
                }
            }
            .subscribe({
                ldList.value = it.results
            },Throwable::printStackTrace).disposedBy(disposeBag)
    }

    fun getSearchAlbumResult(term:String): Observable<ArrayList<ResultItem>> {
        return CodeTestService.NO_AUTH_API.searchAlbum(term)
            .map { it.results }
    }
}