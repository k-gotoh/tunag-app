package jp.html5api.tunag_app

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import jp.html5api.tunag_app.data.db.AppDatabase
import jp.html5api.tunag_app.home.MainActivity.Companion.PREF_NAME

//import jp.html5api.tunag_app.di.AppComponent

class TunagApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
        lateinit var mainKey: MasterKey
        lateinit var prefs: SharedPreferences
//        lateinit var appComponent: AppComponent


    }


    //
    fun saveToken(token: String) {
        with(prefs.edit()) {
            putString("token", token)
            apply()
        }
    }
    fun getToken(): String {
       return  prefs.getString("token", "")?:""
    }

    fun saveMe(me: String) {
        with(prefs.edit()) {
            putString("me", me)
            apply()
        }
    }
    fun getMe() :String {
        return  prefs.getString("me", "")?:""
    }
    fun getMyName() :String {
        return  prefs.getString("myName", "")?:""
    }

    fun saveMyName(me: String) {
        with(prefs.edit()) {
            putString("myName", me)
            apply()
        }
    }

    fun isLogin() :Boolean {
        return  prefs.getBoolean("isLogin", false)
    }

    fun saveLogin(isLogin: Boolean) {
        Log.d("***", "isLogin:"+isLogin)
        with(prefs.edit()) {
            putBoolean("isLogin", isLogin)
            apply()
        }
    }

    override fun onCreate() {
        super.onCreate()
        mainKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        prefs = EncryptedSharedPreferences.create(
            applicationContext,
            PREF_NAME,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }
}

