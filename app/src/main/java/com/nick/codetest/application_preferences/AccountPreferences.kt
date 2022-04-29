package com.nick.codetest.application_preferences

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nick.codetest.entity.UserProfile

/**
 *      Store response data
 *      Init once to update all public static fields from shared preference
 *      & before call api
 */
class AccountPreferences private constructor(context: Context) {

    private val preferences: SharedPreferences
    private lateinit var mContext: Context
    var gson = Gson()

    private var _userId: Int
        get() = preferences.getInt(USER_ID_KEY, -1)
        set(it) {
            val editor = preferences.edit()
            editor.putInt(USER_ID_KEY, it)
            editor.apply()
        }

    private var _token: String?
        get() = preferences.getString(TOKEN_KEY, null)
        set(it) {
            val editor = preferences.edit()
            editor.putString(TOKEN_KEY, it)
            editor.apply()
        }
    private var _userProfile: UserProfile?
        get() {
            val jsonStr = preferences.getString(USER_PROFILE, null)
            jsonStr?.let {
                return gson.fromJson(it, UserProfile::class.java)
            }?: return null
        }
        set(it) {
            val editor = preferences.edit()
            val jsonStr = gson.toJson(it)
            editor.putString(USER_PROFILE, jsonStr)
            editor.apply()
        }


//    private var _realName: String?
//        get() = preferences.getString(REAL_NAME_KEY, null)
//        set(it) {
//            val editor = preferences.edit()
//            editor.putString(REAL_NAME_KEY, it)
//            editor.apply()
//        }

//    private var _mobileNum: String?
//        get() = preferences.getString(MOBILE_NUM_KEY, null)
//        set(it) {
//            val editor = preferences.edit()
//            editor.putString(MOBILE_NUM_KEY, it)
//            editor.apply()
//        }

    private fun clearPreferenceData() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    init {
        preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
        fetchPreferenceData()
    }

//    fun storeSessionResponse(response: Session) {
//        userId = response.userId
//        _userId = response.userId
//        token = response.token
//        _token = response.token
//
//        // TODO remove JPUSH
//        // JPUSH set alias
////        JPushInterface.setAlias(mContext, JPUSH_sequence, response.userId.toString())
//    }

//    fun storeLoginResponse(response: TokenResponse){
//        safeLet(response.userId,response.token){mUserId,mToken->
//            userId = mUserId
//            _userId = mUserId
//            token = mToken
//            _token = mToken
//        }
//    }

//    fun storeUserProfile(data: UserProfile?){
//        data?.let{
//            userProfile = it
//            _userProfile = it
//        }
//
//    }


//    fun storeMobileNumber(number: String) {
//        mobileNum = number
//        _mobileNum = number
//    }

    private fun fetchPreferenceData() {
        userId = if (_userId == -1) {
            null
        } else {
            _userId
        }
        token = _token
        userProfile = _userProfile
    }

    /**
     *  Clear when logout or token expire
     */
    fun clear() {
        userId = null
        token = null
        realName = null
        userProfile = null

        clearPreferenceData()

        // TODO remove JPUSH
        // JPUSH delete Alias
//        JPushInterface.deleteAlias(mContext, JPUSH_sequence)
    }

    companion object {
        const val PREFERENCES_FILE = "account"

        // KEY
        private const val USER_ID_KEY = "userId"
        private const val TOKEN_KEY = "token"

        //UserProfile
        private const val USER_PROFILE = "userProfile"

        //        private const val REAL_NAME_KEY = "realName"
        private const val MOBILE_NUM_KEY = "mobileNum"

        // One Signal - User id Tag
        private val PUSH_NOTIFICATION_USER_TAG = "USER_ID"


        //UserProfile
        var userProfile:UserProfile? = null

        // SessionResponse
        var userId: Int? = null
        var token: String? = null
        var realName: String? = null
        var mobileNum: String? = null

        var applicationTargetVersion: String = ""
        var isNewAppAvailable = false
        var isAppForceUpdate = false
        var applicationChangeLog: String = ""
        var APP_DOWNLOAD_LINK: String = ""

        private var instance: AccountPreferences? = null

        fun initialize(context: Context) {
            getInstance(context)
        }

        fun getInstance(context: Context): AccountPreferences {
            if (instance == null) {
                instance = AccountPreferences(context)
            }

            return instance as AccountPreferences
        }

        fun setAppChangeLog(changeLog: Array<String>) {
            var output = ""
            changeLog.forEach { output = output+it+"\n" }
            applicationChangeLog = output
        }

    }
}