package com.nick.codetest

import android.widget.Toast
import androidx.multidex.MultiDexApplication
import com.nick.codetest.application_preferences.AccountPreferences
import com.nick.codetest.application_preferences.ApplicationPreferences
import com.nick.codetest.entity.ResultItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class CodeTestApplication: MultiDexApplication() {
    companion object {
        var APP_VERSION: String = System.getenv("APP_VERSION") ?: "0.0.1"
        // true: forced logout, false: user logout
        val LOGOUT_SUBJECT = PublishSubject.create<Boolean>()
        val BOOKMARK_SUBJECT = PublishSubject.create<ArrayList<ResultItem>>()

        var PUSHY_TOKEN: String = ""

    }

    override fun onCreate() {
        super.onCreate()

        AccountPreferences.initialize(applicationContext)
        ApplicationPreferences.getInstance(applicationContext)

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
//                val intent = Intent(applicationContext, LoginOrRegisterActivity::class.java)
//                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK )
//                startActivity(intent)
            }else{
                Toast.makeText(applicationContext, "登出成功", Toast.LENGTH_LONG).show()
            }


        }
    }

}