package com.nick.codetest.retrofit.interceptor

import com.nick.codetest.application_preferences.AccountPreferences
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

open class NetInterceptor : BaseInterceptor() {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestOld = chain.request()
        val builder = requestOld.newBuilder()

        setHeader(builder)

        // append parameter v to every get request, prevent caching
//        val url = preventCacheForGet(requestOld)
        val url = requestOld.url

        // build a new request
        val requestNew = builder
            .url(url)
            .build()

        return chain.proceed(requestNew)
    }

    private fun setHeader(builder: Request.Builder) {
        // add header for every request
        AccountPreferences.token?.let {
            builder.header("Authorization", "Token $it")
        }
    }

    fun preventCacheForGet(request: Request): HttpUrl {
        if (request.method.equals("GET", true)) {
            return request.url
        }

        return request.url
    }
}