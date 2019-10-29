package com.wso2.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.app.KeyguardManager
import android.hardware.fingerprint.FingerprintManager
import android.hardware.biometrics.BiometricPrompt
import android.hardware.biometrics.BiometricManager
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.google.firebase.iid.FirebaseInstanceId
import java.lang.NullPointerException
import kotlin.Result.Companion.success


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent1 = getIntent()


        findViewById<Button>(R.id.RegisterButton).setOnClickListener {
            Log.i("RegButton ", "user clicked Register button to scan QR code")

            //send the http "NO" response to biometric authenticator
        }

        val token = FirebaseInstanceId.getInstance().token
        Log.i("MYTAG", "This is your Firebase token :  " + token!!)

        //val intent1 = getIntent()
        Log.i("MYTAG", "came here 1")

//        val bundle: Bundle? = intent.getBundleExtra("sessionkey")
//        Log.d("Came here 1 ", intent.extras.toString())

        try {
            val messageSDK = intent1.getStringExtra("sessionkey")
            val messageCHALLENGE = intent1.getStringExtra("Challenge")
            Log.i("MYTAG","this ish session Data Key  "+ messageSDK)
            Log.i("MYTAG","this is sthe challenge  "+ messageCHALLENGE)


//            val messageSDK = bundle!!.get("sessionkey").toString()
//            val messageCHALLENGE = bundle!!.getString("Challenge").toString() // 1
//        var strUser: String = intent.getStringExtra("value") // 2


            val executor = Executors.newSingleThreadExecutor()
            val activity: FragmentActivity = this // reference to activity

            val biometricPrompt = androidx.biometric.BiometricPrompt(
                activity,
                executor,
                object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {

                    //            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                super.onAuthenticationError(errorCode, errString)
//                Log.i("error code issq : ",errorCode.toString())
//                Log.d("error string iss : ",errString.toString())
//                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
//                    Log.i("user clicked the 'close' button","   hi")
//                    // user clicked negative button
//                } else {
//                }
//            }
                    override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        if (messageSDK != null && messageCHALLENGE != null) {
                            Log.i("MYTAG",messageSDK)
                            Log.i("MYTAG",messageCHALLENGE)
                            Log.i("MYTAG","came here 1.2  ")
                            success1(messageSDK, messageCHALLENGE)
                        }
                    }
//            override fun onAuthenticationFailed() {
//                super.onAuthenticationFailed()
//                success1()
//                TODO("Called when a biometric is valid but not recognized.")
//            }
                })
            val promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock with your fingerprint.")
                .setNegativeButtonText("Close")
                .build()
            findViewById<Button>(R.id.authenticateButton).setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
            }
            findViewById<Button>(R.id.denyButton).setOnClickListener {
                Log.i("DENYBUTTON1 ", "user clicked deny botton")
                //send the http "NO" response to biometric authenticator
            }
        }
        catch (e: NullPointerException) {
            Log.i("error ", "this iss a null pointer error")


        }
        fun onTokenRefreshed(token: String) {
            // from here you can save application token in your app server.
        }
    }
    private fun success1(sdk: String?, challenge: String?) {

        //val intent1 = getIntent()
        Log.i("MYTAG","came here 2 ")
        val intent3 = Intent(this, Activity3::class.java)
        Log.i("MYTAG","came here 3 ")
        intent3.putExtra("sessionkey", sdk)
        intent3.putExtra("Challenge", challenge)
        Log.i("MYTAG",sdk.toString())
        Log.i("MYTAG",challenge)
        Log.i("MYTAG","came here 4 ")
        startActivity(intent3)
    }
}
