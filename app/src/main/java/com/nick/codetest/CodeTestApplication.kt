package com.nick.codetest

import android.content.Intent
import android.widget.Toast
import androidx.multidex.MultiDexApplication
import com.nick.codetest.application_preferences.AccountPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class CodeTestApplication: MultiDexApplication() {
    companion object {
        var APP_VERSION: String = System.getenv("APP_VERSION") ?: "0.0.1"
        // true: forced logout, false: user logout
        val LOGOUT_SUBJECT = PublishSubject.create<Boolean>()

        var PUSHY_TOKEN: String = ""

    }

    override fun onCreate() {
        super.onCreate()

        AccountPreferences.initialize(applicationContext)

//        WorkerPlatformService.NO_HEADER_API.getRegionInfo(1).doOnNext {
//            Log.e("test111",it.results?.size.toString()+"")
//        }.subscribe({},{e->
//            Log.e("test111",e.localizedMessage)
//        })

        val d = LOGOUT_SUBJECT//.debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { AccountPreferences.token != null }
            .subscribe { forced -> logout(forced) }
    }

    private fun logout(forced:Boolean){
        AccountPreferences.token?.let {
            AccountPreferences.getInstance(applicationContext).clear()

            if (forced){
                Toast.makeText(applicationContext, "登錄已過期，請重新登入", Toast.LENGTH_LONG).show()
                // go to login page
                // todo not for this app
//                val intent = Intent(applicationContext, LoginOrRegisterActivity::class.java)
//                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK )
//                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "登出成功", Toast.LENGTH_LONG).show()
            }


        }
    }

}