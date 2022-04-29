package com.nick.codetest.application_preferences

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat

class ApplicationPreferences private constructor(context: Context) {

    private val preferences: SharedPreferences
//    private val passwordStorage: PasswordStorageHelper




    init {
        preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
//        passwordStorage = PasswordStorageHelper(context)
    }

//    fun setPasswordSaveEnabled(isEnabled: Boolean) {
//        val editor = preferences.edit()
//        editor.putBoolean(IS_PASSWORD_SAVED_KEY, isEnabled)
//        editor.apply()
//    }

    fun disableTrcInitialCheck() {
        val editor = preferences.edit()
        editor.putBoolean(TRC_INITIAL_CHECK_KEY, true)
        editor.apply()
    }

//    fun savePassword(password: String) {
//        passwordStorage.setData(PASSWORD_KEY, password.toByteArray())
//    }
//
//    fun saveLastNotifyDate(date:Long){
//        lastTimeVisitNotifyActivityDate = date
//    }
//
//    fun clearSavedPassword() {
//        setPasswordSaveEnabled(false)
//        passwordStorage.remove(PASSWORD_KEY)
//    }

    fun saveSessionResponse(userId: Int, token: String) {
        val editor = preferences.edit()
        editor.putInt(USER_ID_KEY, userId)
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun saveOrderAutoConfirm(isEnabled: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_ORDER_AUTO_CONFIRM_KEY, isEnabled)
        editor.apply()

//        SmsReceiver.ENABLE_POST_MESSAGE = isEnabled
    }


    companion object {
        const val PREFERENCES_FILE = "settings"

//        private const val LAST_TIME_NOTIFY_DATE = "NOTIFY_DATE"

        private const val USERNAME_KEY = "username"
        private const val PASSWORD_KEY = "password"
        private const val TRC_INITIAL_CHECK_KEY = "TRC_INITIAL"
        private const val IS_PASSWORD_SAVED_KEY = "is_password_saved"
        private const val IS_ORDER_AUTO_CONFIRM_KEY = "is_order_auto_confirm"

        // Session Response
        private const val USER_ID_KEY = "userId"
        private const val TOKEN_KEY = "token"

        private var instance: ApplicationPreferences? = null

        fun getInstance(context: Context): ApplicationPreferences {
            if (instance == null) {
                instance = ApplicationPreferences(context)
            }

            return instance as ApplicationPreferences
        }
    }
}