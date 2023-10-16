package jp.html5api.tunag_app.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import jp.html5api.tunag_app.R
import jp.html5api.tunag_app.model.PushData

class MyFirebaseMessagingService() : FirebaseMessagingService(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("*****", "onMessageReceived: " + remoteMessage.data["message"])

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        val channelId = "Default"
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(remoteMessage.notification!!.title)
            .setContentText(remoteMessage.notification!!.body).setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            "Default channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        Log.d("*****", "title: " + remoteMessage.notification!!.title)
        Log.d("*****", "body: " + remoteMessage.notification!!.body)
        val pushData =  Gson().fromJson(remoteMessage.notification!!.body, PushData::class.java)

         val sendIntent = Intent(getString(R.string.intent_push)).also {
             it.putExtra("user", pushData.user)
             it.putExtra("message", pushData.message)
             it.putExtra("name", pushData.name)
         }
        sendBroadcast(sendIntent)
        Log.d("*****", "sendBroadcast ")

        manager.createNotificationChannel(channel)
        manager.notify(0, builder.build())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        Log.d("*****", "writeToParcel: ")
    }

    override fun describeContents(): Int {
        Log.d("*****", "describeContents: ")
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyFirebaseMessagingService> {
        override fun createFromParcel(parcel: Parcel): MyFirebaseMessagingService {
            Log.d("*****", "createFromParcel: ")
            return MyFirebaseMessagingService(parcel)
        }

        override fun newArray(size: Int): Array<MyFirebaseMessagingService?> {
            Log.d("*****", "newArray: ")
            return arrayOfNulls(size)
        }
    }
}