package com.nick.codetest.retrofit.interceptor

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nick.codetest.ext.tryParse
import com.nick.codetest.retrofit.exception.CustomException
import com.nick.codetest.retrofit.exception.MyException
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.net.HttpURLConnection
import java.util.*

class LoggingInterceptor : BaseInterceptor() {

    val gson = Gson()
    val type = object: TypeToken<List<String>>() {}.type

    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        logger.i(
            "LoggingInterceptor t1 - :" + String.format(
                "Sending request %s on %s%n%s",
                request.url, chain.connection(), request.headers
            )
        )

        if (request.method.equals("POST", true)) {
            val body = request.body

            logger.i(
                "LoggingInterceptor POST body - :" + String.format(
                    "Sending request %s body: %s",
                    request.url, requestBodyToString(body)
                )
            )

        }

        val response = chain.proceed(request)
        var body = "null"

        logger.i(
            "LoggingInterceptor POST response - :"
                    + if (response.isSuccessful) "Successful" else "not Successful"
        )
//        Log.e("fav","${response.message},${response.code}")
        response.body?.let {
            body = it.string()
        }

        val t2 = System.nanoTime()
        logger.i(
            "LoggingInterceptor t2 - :" + String.format(
                Locale.TRADITIONAL_CHINESE,
                "Received response for %s in %.1fms%n%s%s%s%s",
                response.request.url, (t2 - t1) / 1e6, "",response.code,":",body
            )
        ) // ignore response header first
        //                response.request().url(), (t2 - t1) / 1e6d, response.headers(), body));

        if (!response.isSuccessful) {
            validateDjangoError(request, body, response.code)
        }

        return if (response.body == null) {
            response
        } else {
            response.newBuilder()
                .body(ResponseBody.create(response.body!!.contentType(), body))
                .build()
        }

    }

    @kotlin.jvm.Throws(IOException::class)
    private fun requestBodyToString(requestBody: RequestBody?): String {
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        return buffer.readUtf8()
    }

    private fun validateDjangoError(request: Request, body: String, status: Int) {
        val path = request.url.encodedPath

        //CustomException message
        if (body.contains("error message....")) {
            throw CustomException(path, "some kind of error")
        }


//        val detailError = gson.tryParse<DjangoMessage>(body, DjangoMessage.GSON_TYPE)
//        if (detailError.first) {
//            detailError.second?.message?.let {
//                throw DjangoException(path, it)
//            }
//        }
        val errors = gson.tryParse<List<String>>(body, type)
        if (errors.first) {
            errors.second?.firstOrNull()?.let {
                throw MyException(path, it)
            }
        }
    }

}