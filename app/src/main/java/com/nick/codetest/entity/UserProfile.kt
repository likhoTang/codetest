package com.nick.codetest.entity
import com.google.gson.annotations.SerializedName

class UserProfile {
    @SerializedName("user_id")
    var userId:Int?=null
    @SerializedName("user_name")
    var userName:String?=null
}