package com.nick.codetest.retrofit

import android.util.Log
import com.google.gson.Gson
import com.nick.codetest.BuildConfig
import com.nick.codetest.ext.runInBackground
import com.nick.codetest.retrofit.apiModel.CodeTestModel
import com.nick.codetest.retrofit.interceptor.LoggingInterceptor
import com.nick.codetest.retrofit.interceptor.NetInterceptor
import com.nick.codetest.retrofit.response.SearchResponse
import com.nick.codetest.util.JSONBuilder
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CodeTestService {
    companion object {
        const val TAG = "CodeTestService"

        var currentBaseUrl: String = ""

        private const val TIMEOUT_CONNECTION = 60L
        private const val TIMEOUT_READ = 60L

        private var mInstance: CodeTestService? = null
        val instance: CodeTestService
            get() {
                return mInstance ?: CodeTestService()
            }

        private val mDomainUrl: String
            get() {
                Log.e("main domain", BuildConfig.APP_ENTRANCE_URL.split(";").first())
                return BuildConfig.APP_ENTRANCE_URL.split(";").first()
            }

        private val gson = Gson()
    }

    private lateinit var client: OkHttpClient
    private lateinit var noHeaderClient: OkHttpClient

    private lateinit var mServiceModel: CodeTestModel
    private lateinit var mNoHeaderServiceModel: CodeTestModel

    init {
        mInstance = this

        client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
            .addInterceptor(NetInterceptor())
            .addInterceptor(LoggingInterceptor())
            .build()

        noHeaderClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECTION.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
            .addInterceptor(LoggingInterceptor())
            .build()

        setBaseUrl(mDomainUrl)
    }

    @Synchronized
    fun setBaseUrl(base: String) {

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(base)
//            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        mServiceModel = retrofit.create(CodeTestModel::class.java)


        val retrofit2 = Retrofit.Builder()
            .client(noHeaderClient)
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        mNoHeaderServiceModel = retrofit2.create(CodeTestModel::class.java)

        currentBaseUrl = base
    }

    object AUTH {}
    object NO_AUTH_API{
        fun searchAlbum(term:String):Observable<SearchResponse>{
            return instance.mNoHeaderServiceModel.searchAlbum(term).runInBackground()
        }


    }
}