package com.wso2.test1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import com.wso2.test1.MyFirebaseMessagingService
import java.lang.NullPointerException
import java.net.URLEncoder
import android.R.string
import android.content.Context
import android.os.AsyncTask
import android.os.AsyncTask.execute
//import java.awt.PageAttributes.MediaType
import java.io.*
import okhttp3.MediaType.Companion.toMediaType
import android.os.StrictMode;
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import androidx.annotation.RequiresApi
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.Volley
import com.babylon.certificatetransparency.certificateTransparencyHostnameVerifier
import okhttp3.*
import org.xml.sax.InputSource
import javax.xml.parsers.SAXParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory
import java.lang.reflect.Method
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import javax.net.ssl.*
import javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier
import javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory
//import com.wso2.test1.createOkHttpClient
//import com.wso2.test1.SslUtils



class Activity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_3)

        val intent = getIntent()

        try {
            val messageSDK = intent.getStringExtra("sessionkey")
            val messageCHALLENGE = intent.getStringExtra("Challenge")
            //val messageCHALLENGE = "qqqqqqqqqqqqqqqqqqqqqqqpyt"
            Log.i("MYTAG", "this ish session Data Key in  act 3 " + messageSDK)
            Log.i("MYTAG", "this is sthe challenge in act 3 " + messageCHALLENGE)

            val requestURL="https://biometricauthenticator.private.wso2.com:9443/samlbiomtriccheck?t=mobile&SDKMobile=" + messageSDK + "&CHALLENGEMobile=" + messageCHALLENGE
            //val requestURL="https://biometricauthenticator.private.wso2.com:9443/carbon"
            Log.i("MYTAG","came here 12")
            NetworkTask().execute(requestURL, messageCHALLENGE)
            Log.i("MYTAG","came here 13")

        } catch (e: Exception) {
            Log.i("errorrrr ", "this iss a null pointer error in activity 3",e)
        }

        //MyFirebaseMessagingService.sendGet()
    }
}

@RequiresApi(Build.VERSION_CODES.CUPCAKE)

private class NetworkTask : AsyncTask<String, Int, Long>() {
    override fun doInBackground(vararg parts: String): Long? {

        val requestURL = parts.first()
        val queryString = parts.last()
        Log.i("MYTAG","came here 6")
        val connection: HttpsURLConnection = URL(requestURL).openConnection() as HttpsURLConnection
        //connection.setHostnameVerifier({_,_ -> true})
       // connection.setHostnameVerifier(requestURL,null)
//        connection.hostnameVerifier = certificateTransparencyHostnameVerifier(connection.hostnameVerifier) {
//            // Enable for the provided hosts
//            +"https://10.10.10.177:9443"
//            // Exclude specific hosts
////                -"10.10.10.78:9443"
//        }

        Log.i("MYTAG","came here 4.0")

        try {
            Log.i("MYTAG","came here 5.0")
//                connection.setHostnameVerifier { s, sslSession -> true }
//                Log.i("MYTAG","came here 6.0")
//                ConnectionUtils.openReadConnection("https://10.10.10.78:9443")
//                ConnectionUtils.verify1()

                //UnsafeOkHttpClient.getUnsafeOkHttpClient("10.10.10.177")
            Log.i("MYTAG","came here 6.1")
                // Default is GET so you must override this for post
            connection.requestMethod = "POST"
            Log.i("MYTAG","came here 6.2")
                // To send a post body, output must be true
            connection.doOutput = true
            Log.i("MYTAG","came here 6.3")


            val outputStream: OutputStream = connection.outputStream
            Log.i("MYTAG","came here 6.4")
                // Create a writer container to pass the output over the stream
            val outputWriter = OutputStreamWriter(outputStream)
            Log.i("MYTAG","came here 6.5")
                // Add the string to the writer container
            outputWriter.write(queryString)
            Log.i("MYTAG","came here 7")
                // Send the data
            outputWriter.flush()


            }
            catch (e: SSLHandshakeException){
                Log.i("error in 3 ","againn this errorr")
            }




        // Create an input stream to read the response
        val inputStream = BufferedReader(InputStreamReader(connection.inputStream)).use {
            // Container for input stream data
            val response = StringBuffer()
            Log.i("MYTAG","came here 8")
            var inputLine = it.readLine()
            // Add each line to the response container
            while (inputLine != null) {
                Log.i("MYTAG","came here 9")
                response.append(inputLine)
                inputLine = it.readLine()
            }
            Log.i("MYTAG","came here 10")
            it.close()
            // TODO: Add main thread callback to parse response
            println(">>>> Response: $response")
        }
        Log.i("MYTAG","came here 11")
        connection.disconnect()

        return 0
    }


    protected fun onProgressUpdate(vararg progress: Int) {
    }

    override fun onPostExecute(result: Long?) {

        Log.i("MYTAG","came here 11.1")
    }
}
//
//private fun UnsafeOkHttpClient.Companion.getUnsafeOkHttpClient(context: String) {
//}


//interface xy : HostnameVerifier {
//    fun verify1(var1: String): Boolean
//}



//private fun disableSSLCertificateChecking() {
//    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//        val acceptedIssuers1: Array<X509Certificate>?
//            get()= null
//
//        @Throws(CertificateException::class)
//        override fun checkClientTrusted(arg0: Array<X509Certificate>, arg1: String) {
//            // Not implemented
//        }
//
//        @Throws(CertificateException::class)
//        override fun checkServerTrusted(arg0: Array<X509Certificate>, arg1: String) {
//            // Not implemented
//        }
//    })
//
//    try {
//        val sc = SSLContext.getInstance("TLS")
//
//        sc.init(null, trustAllCerts, java.security.SecureRandom())
//
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
//    } catch (e: KeyManagementException) {
//        e.printStackTrace()
//    } catch (e: NoSuchAlgorithmException) {
//        e.printStackTrace()
//    }
//
//}

//interface X509TrustManager : TrustManager {
//
//    val acceptedIssuers: Array<X509Certificate>
//    @Throws(CertificateException::class)
//    fun checkClientTrusted(var1: Array<X509Certificate>, var2: String)
//
//    @Throws(CertificateException::class)
//    fun checkServerTrusted(var1: Array<X509Certificate>, var2: String)
//}










