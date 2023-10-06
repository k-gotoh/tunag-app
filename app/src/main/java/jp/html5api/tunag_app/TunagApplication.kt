package jp.html5api.tunag_app

import android.app.Application
import androidx.room.Room
import jp.html5api.tunag_app.data.db.AppDatabase
//import jp.html5api.tunag_app.di.AppComponent

class TunagApplication : Application() {

    companion object {
        lateinit var database: AppDatabase
//        lateinit var appComponent: AppComponent
    }
//
    override fun onCreate() {
        super.onCreate()
        // AppDatabaseをビルドする
//        database = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "talk_db"
//        ).build()
    }
}

