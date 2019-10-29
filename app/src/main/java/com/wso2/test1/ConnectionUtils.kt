package com.wso2.test1
//
//import sun.net.sdp.SdpSupport.createSocket
//import com.sun.xml.internal.ws.util.StringUtils
import android.util.Log
import java.io.IOException
import java.net.*
import java.security.GeneralSecurityException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import javax.net.SocketFactory
import javax.net.ssl.*


object ConnectionUtils {

    internal val CHARSET: String

    private val SSL_CONTEXT: SSLContext?

    private val HOSTNAME_VERIFIER: DummyHostnameVerifier

    init {
        var context: SSLContext? = null
        try {
            context = SSLContext.getInstance("SSL")
           // context!!.init(null, arrayOf<TrustManager>(DummyTrustManager()), SecureRandom())
        } catch (t: Throwable) {
            t.printStackTrace()
        }

        SSL_CONTEXT = context
        HOSTNAME_VERIFIER = DummyHostnameVerifier()
        CHARSET = "UTF-8"
    }
//
//    fun setAuthorization(conn: URLConnection, username: String, password: CharArray?) {
//        if (!StringUtils.isEmpty(username) && password != null && password.size > 0) {
//            conn.setRequestProperty(
//                "Authorization",
//                "Basic " + Base64.encodeBytes((username + ":" + String(password)).toByteArray())
//            )
//        }
//    }

    @Throws(IOException::class)
    fun openReadConnection(url: String): URLConnection {
        val conn = openConnection(url)
        conn.setRequestProperty("Accept-Charset", CHARSET)
        return conn
    }

    @Throws(IOException::class)
    fun openConnection(url: String): URLConnection {
        val urlObject = URL(url)
        val conn = urlObject.openConnection()
        //setAuthorization(conn, username, password)
        conn.setUseCaches(false)
        conn.setDoOutput(true)
        if (conn is HttpsURLConnection) {
            val secureConn = conn as HttpsURLConnection
            //secureConn.setSSLSocketFactory(SSL_CONTEXT!!.getSocketFactory())

            //run the code snippet to bypass certificate validation
            disableSSLCertificateChecking()

            secureConn.setHostnameVerifier(HOSTNAME_VERIFIER)
        }
        return conn
    }
    @Throws (IOException::class)
    fun verify1 (){
        DummyHostnameVerifier()
    }

    // Copyright (C) 2009 The Android Open Source Project
    //
    // Licensed under the Apache License, Version 2.0 (the "License");
    // you may not use this file except in compliance with the License.
    // You may obtain a copy of the License at
    //
    // http://www.apache.org/licenses/LICENSE-2.0
    //
    // Unless required by applicable law or agreed to in writing, software
    // distributed under the License is distributed on an "AS IS" BASIS,
    // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    // See the License for the specific language governing permissions and
    // limitations under the License.
//    class BlindSSLSocketFactory private constructor(private val sslFactory: SSLSocketFactory) :
//        SSLSocketFactory() {
//
//        val defaultCipherSuites: Array<String>
//            get() = sslFactory.getDefaultCipherSuites()
//
//        val supportedCipherSuites: Array<String>
//            get() = sslFactory.getSupportedCipherSuites()
//
//        @Throws(IOException::class)
//        fun createSocket(s: Socket, host: String, port: Int, autoClose: Boolean): Socket {
//            return sslFactory.createSocket(s, host, port, autoClose)
//        }
//
//        @Throws(IOException::class)
//        fun createSocket(): Socket {
//            return sslFactory.createSocket()
//        }
//
//        @Throws(IOException::class, UnknownHostException::class)
//        fun createSocket(host: String, port: Int): Socket {
//            return sslFactory.createSocket(host, port)
//        }
//
//        @Throws(IOException::class)
//        fun createSocket(host: InetAddress, port: Int): Socket {
//            return sslFactory.createSocket(host, port)
//        }
//
//        @Throws(IOException::class, UnknownHostException::class)
//        fun createSocket(
//            host: String, port: Int, localHost: InetAddress,
//            localPort: Int
//        ): Socket {
//            return sslFactory.createSocket(host, port, localHost, localPort)
//        }
//
//        @Throws(IOException::class)
//        fun createSocket(
//            address: InetAddress, port: Int,
//            localAddress: InetAddress, localPort: Int
//        ): Socket {
//            return sslFactory.createSocket(address, port, localAddress, localPort)
//        }
//
//        companion object {
//            private val INSTANCE: BlindSSLSocketFactory
//
//            init {
//                try {
//                    val context = SSLContext.getInstance("SSL")
//                    val trustManagers = arrayOf<TrustManager>(DummyTrustManager())
//                    val rng = SecureRandom()
//                    context.init(null, trustManagers, rng)
//                    INSTANCE = BlindSSLSocketFactory(context.getSocketFactory())
//                } catch (e: GeneralSecurityException) {
//                    throw RuntimeException("Cannot create BlindSslSocketFactory", e)
//                }
//
//            }
//
//            val default: SocketFactory
//                get() = INSTANCE
//        }
//    }

    /**
     * DummyTrustManager trusts all certificates.
     *
     * @author James Moger
//     */
//    private abstract class DummyTrustManager : X509TrustManager {
//
//        val acceptedIssuers: Array<X509Certificate>?
//            get() = null
//
//        @Throws(CertificateException::class)
//        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
//        }
//
//        @Throws(CertificateException::class)
//        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
//        }
//    }

    /**
     * Trusts all hostnames from a certificate, including self-signed certs.
     *
     * @author James Moger
     */
    private class DummyHostnameVerifier : HostnameVerifier {
        override fun verify(p0: String?, p1: SSLSession?): Boolean {
            Log.i("x","returns true from connectionutils")
            return true
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }

    private fun disableSSLCertificateChecking() {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }



//            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager() {
//                override fun getAcceptedIssuers(): Array<X509Certificate> {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }



            private val acceptedIssuers: Array<X509Certificate>? = null

            @Throws(CertificateException::class)
            override fun checkClientTrusted(arg0: Array<X509Certificate>, arg1: String) {
                // Not implemented
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(arg0: Array<X509Certificate>, arg1: String) {
                // Not implemented
            }
        })

        try {
            val sc = SSLContext.getInstance("TLS")

            sc.init(null, trustAllCerts, java.security.SecureRandom())

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)

            HttpsURLConnection.setDefaultHostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String, session: SSLSession): Boolean {
                    return true
                }
            })
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

    }
}
