package com.nick.codetest.retrofit.response

import com.google.gson.annotations.SerializedName
import com.nick.codetest.entity.ResultItem

class SearchResponse {
    @SerializedName("resultCount")
    var resultCount:Int?=null
    @SerializedName("results")
    var results:ArrayList<ResultItem> = arrayListOf()
}