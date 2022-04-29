package com.nick.codetest.retrofit.exception

import java.io.IOException

open class MyException(val path: String, val msg: String): IOException(msg) {
    companion object {
        fun empty(): MyException {
            return MyException("", "")
        }
    }

    override fun getLocalizedMessage(): String {
        return "$path $msg"
    }
}

class CustomException(path: String, msg: String): MyException(path, msg) { }