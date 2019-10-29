package com.wso2.test1

//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.ContentValues.TAG
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.util.Log
//import android.util.Log.d
import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import com.google.firebase.messaging.FirebaseMessagingService
//import com.google.firebase.messaging.RemoteMessage

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "FirebaseMessagingService"

    @SuppressLint("LongLogTag")
    override fun onMessageReceived(remoteMessage: RemoteMessage){


        Log.d(TAG, "Dikirim dari: ${remoteMessage.from}")

        if (remoteMessage.notification != null) {
            remoteMessage.data.isNotEmpty().let {
                Log.i(TAG, "challenge in the Message data payload: " + remoteMessage.data.get("challenge"))
                Log.i(TAG, "SDK in the Message data payload: " + remoteMessage.data.get("sessionkey"))

                val myUuid = remoteMessage.data
                Log.i("heres myy remote message received: ",myUuid.toString())

                val sessionDataKey1=remoteMessage.data.get("challenge")
                val challenge1=remoteMessage.data.get("sessionkey")

                //return sessionDataKey1
                showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body, sessionDataKey1,challenge1)




            }

        }




//        remoteMessage.data.isNotEmpty().let {
//                    Log.d(TAG, "Message data payload: " + remoteMessage.data)
//
//    }


//        if (remoteMessage.data != null) {
//            showNotification(remoteMessage.data?.title, remoteMessage.data?.body)
//        }


    }



    private fun showNotification(title: String?, body: String?,sessionDataKey2: String?, challenge2:String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("sessionkey",sessionDataKey2)
        intent.putExtra("Challenge",challenge2)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())


            //Log.i(TAG, "newwwww Message data payload: " + remoteMessage.notification)
        Log.i(TAG, "titleeee: " + title)
        Log.i(TAG, "thisis the message: " + body)
        Log.i(TAG,"now its Session data key 2: "+sessionDataKey2)
        Log.i(TAG,"now its challenge 2: "+challenge2)

    }
}

//import android.support.v4.app.NotificationCompat

//private val Nothing?.mewLogs: String?
//    get() {return null }
//
//private val Unit.fcmToken: Unit
//    get() {}
//
//class MyFirebaseMessagingService : FirebaseMessagingService() {
//    val TAG = "FirebaseMessagingService"
//    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//        // Handle FCM messages here.
//        // If the application is in the foreground handle both data and notification messages here.
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated.
//        d("firebase", "From: " + remoteMessage!!.from)
//        d("firebase", "Notification Message Body: " + remoteMessage.notification.body!!)
//        Log.d(TAG, "From: ${remoteMessage.from}")
//
//    // Check if message contains a data payload.
//    remoteMessage.data.isNotEmpty().let {
//        Log.d(TAG, "Message data payload: " + remoteMessage.data)
//
//    }
//
//}
////
////
////}
//
//    class MyFirebaseMessagingService : FirebaseMessagingService() {
//
//        fun onNewToken(p0: String?) {
//        run { onNewToken(p0) }
//        val mSharedPreference = null
//        val SharedPrefFlag = Unit
//        mSharedPreference!![SharedPrefFlag.fcmToken] = p0
//    }
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
//        super.onMessageReceived(remoteMessage)
//
//        val MewConstants = null
//        d(MewConstants.mewLogs, "notification received: " + remoteMessage)
//
//        createNotificationChannel()
//
//        var notificationData   = remoteMessage?.data
//
//        val title =notificationData?.get("title")
//        val message =notificationData?.get("message")
//        Log.i(TAG, "newwwww Message data payload: " + remoteMessage?.data)
//        Log.i(TAG, "titleeee: " + title)
//        Log.i(TAG, "thisis the message: " + message)
//
//
//        // Create an explicit intent for an Activity in your app
//        val SplashActivity = Unit
//        val intent = Intent(this, SplashActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            putExtra("isFromFCMNotification", true)
//        }
//
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//        val mBuilder = NotificationCompat.Builder(this, "default")
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_MAX)
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
//            // Set the intent that will fire when the user taps the notification
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(this)) {
//            // notificationId is a unique int for each notification that you must define
//            notify(1000, mBuilder.build())
//        }
//    }
//
//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "DEVELOPINE_NOTIFICATION"
//            val descriptionText = "DEVELOPINE_NOTIFICATION_DETAIL"
//            val importance = NotificationManager.IMPORTANCE_HIGH
//
//            val channel = NotificationChannel("default", name, importance).apply {
//                description = descriptionText
//                setShowBadge(true)
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//}
//
//private fun com.wso2.test1.MyFirebaseMessagingService.onNewToken(p0: String?) {
//
//}
//}





//
//class SimpleFirebaseMessagingService : FirebaseMessagingService() {
//
//    private val TAG = "spFirebaseMsgService"
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//
//        // when App is in foreground, notification message:
//        if (remoteMessage.notification != null) {
//            // post notification if needed
//            updateContent(remoteMessage.notification)
//        }
//
//
//
//        // Use data payload to create a notification
//        if (remoteMessage.data.isNotEmpty()) {
//
//            // step 2.1: decrypt payload
//            val notificationMessage = decryptPayload(remoteMessage.data)
//            // step 2.1: display notification immediately
//            sendNotification(notificationMessage)
//            // step 2.2: update app content with payload data
//            updateContent(remoteMessage.data)
//
//            // Optional step 2.3: if needed, fetch data from app server
//            /* if additional data is needed or payload is bigger than 4KB, App server can send a flag to notify client*/
//            if (remoteMessage.data["url"] != null) {
//                //scheduleJob()
//                // use WorkManager when it's stable
//            }
//        }
//        // process notification payload when app in foreground...
//    }
//
//    private fun decryptPayload(dataPayload: Map<String, String>): String {
//        return "decrypted message"
//    }
//
//    private fun sendNotification(notificationMessage: String) {}
//    private fun updateContent(dataPayload: Map<String, String>) {}
//    private fun scheduleWork() {
//        // it's recommended to use WorkManager when it's stable, use JobScheduler
//        // on background work complete, update the notification if still active
//    }
//
//
//        // process data payload
//
//
//    private fun updateContent(notification: RemoteMessage.Notification) {}
//
//
//}




//class SimpleFirebaseMessagingService : FirebaseMessagingService() {
//
//    private val TAG = "spFirebaseMsgService"
//
//
//
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//
//
//
//        // process data payload
//
//        if (remoteMessage.data.isNotEmpty()) {
//
//            // step 2.1: decrypt payload
//            val notificationMessage = decryptPayload(remoteMessage.data)
//            // step 2.1: display notification immediately
//            sendNotification(notificationMessage)
//            // step 2.2: update app content with payload data
//            updateContent(remoteMessage.data)
//
//            // Optional step 2.3: if needed, fetch data from app server
//            /* if additional data is needed or payload is bigger than 4KB, App server can send a flag to notify client*/
//            if (remoteMessage.data["url"] != null) {
//                //scheduleJob()
//                // use WorkManager when it's stable
//            }
//        }
//        // process notification payload when app in foreground...
//    }
//
//    private fun decryptPayload(dataPayload: Map<String, String>): String {
//        return "decrypted message"
//    }
//
//    private fun sendNotification(notificationMessage: String) {}
//    private fun updateContent(dataPayload: Map<String, String>) {}
//    private fun scheduleWork() {
//        // it's recommended to use WorkManager when it's stable, use JobScheduler
//        // on background work complete, update the notification if still active
//    }
//}



