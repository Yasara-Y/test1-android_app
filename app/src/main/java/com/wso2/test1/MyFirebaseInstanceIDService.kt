package com.wso2.test1

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.messaging.FirebaseMessagingService




class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    val TAG = "firebase"
    override fun onTokenRefresh() {

       //String refreshedToken = FirebaseInstanceId.getInstance().getToken()
        //Log.d(TAG,  “Refreshed token: “ + refreshedToken)
        Log.d(TAG, "Refreshed token:  " + FirebaseInstanceId.getInstance().getToken())



    }
}




