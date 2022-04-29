package com.nick.codetest.util

import android.util.Log
import com.google.gson.GsonBuilder

class Logger(name: String?) {

    private val tag = "${javaClass.simpleName}:$name"

    fun debugLog(s: String?) {
        debugLog(s, null)
    }

    fun debugLog(s: String?, dec: String?) {
        var s = s
        try {
            if (s == null) {
                s = "null"
            }
            if (dec != null) {
                Log.d("Logger", "$tag  $dec:\n$s")
            } else {
                Log.d("Logger", "$tag  $s")
            }
            Log.d(tag, "\n--------------------------------------------\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun debugLog(o: Any, dec: String?) {
        try {
            try {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val msg = if (dec.isNullOrEmpty()) {
                    "${o.javaClass.name}:${gson.toJson(o)}"
                } else {
                    "$dec:\n${o.javaClass.name}:${gson.toJson(o)}"
                }

                Log.d("Logger", "$tag $msg")
                Log.d("Logger", "\n--------------------------------------------\n")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun debugLog(o: Any) {
        debugLog(o, null)
    }

    fun i(msg: String?) {
        Log.i(tag, "" + msg)
    }

    fun d(msg: String?) {
        Log.d(tag, "" + msg)
    }

    fun w(msg: String?) {
        Log.w(tag, "" + msg)
    }

    fun e(msg: String?) {
        Log.e(tag, "" + msg)
    }

    companion object {
        operator fun get(tag: String?): Logger {
            return Logger(tag)
        }

        fun get(): Logger? {
            return Logger("")
        }

        fun i(tag: String?, msg: String?) {
            get(tag).i(msg)
        }

        fun d(tag: String?, msg: String?) {
            get(tag).d(msg)
        }

        fun w(tag: String?, msg: String?) {
            get(tag).w(msg)
        }

        fun e(tag: String?, msg: String?) {
            get(tag).e(msg)
        }
    }

}