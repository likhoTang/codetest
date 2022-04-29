package com.nick.codetest.retrofit.apiModel

import com.nick.codetest.retrofit.response.SearchResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.RequestBody
import okhttp3.internal.http.hasBody
import retrofit2.Response
import retrofit2.http.*

interface CodeTestModel {
    //https://itunes.apple.com/search?term=jack+johnson&entity=album

    @GET("search/?entity=album")
    fun searchAlbum(@Query("term")term:String):Observable<SearchResponse>
}