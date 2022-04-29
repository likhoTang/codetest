package com.nick.codetest.retrofit.interceptor

import com.nick.codetest.retrofit.CodeTestService
import com.nick.codetest.util.Logger
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

open class BaseInterceptor : Interceptor {

    protected val logger = Logger(CodeTestService.TAG)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}