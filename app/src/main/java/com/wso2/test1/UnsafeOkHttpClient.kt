package com.wso2.test1

import android.content.Context
import android.util.Log
//import com.wso2.test1.SslUtils.getSslContextForCertificateFile
import okhttp3.OkHttpClient
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager as X509TrustManager1





//
//public fun createOkHttpClient(context: Context): OkHttpClient {
//    var client = OkHttpClient.Builder()
//
////    if (dangerouslyTrustingAllHostsWhichWeWillNeverEverDoInProduction == true)
////        getTrustAllHostsSSLSocketFactory()?.let {
////            client.sslSocketFactory(it)
////        }
//
//    //client.sslSocketFactory(getSslContextForCertificateFile(context, "my_certificate.pem").socketFactory, trustManager = )
//
//
//
//    return client.build()
//
//}
//
//
//
//object SslUtils {
//
//    fun getSslContextForCertificateFile(context: Context, fileName: String): SSLContext {
//        try {
//            val keyStore = SslUtils.getKeyStore(context, fileName)
//            val sslContext = SSLContext.getInstance("SSL")
//            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
//            trustManagerFactory.init(keyStore)
//            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
//            return sslContext
//        } catch (e: Exception) {
//            val msg = "Error during creating SslContext for certificate from assets"
//            e.printStackTrace()
//            throw RuntimeException(msg)
//        }
//    }
//
//    private fun getKeyStore(context: Context, fileName: String): KeyStore? {
//        var keyStore: KeyStore? = null
//        try {
//            val assetManager = context.assets
//            val cf = CertificateFactory.getInstance("X.509")
//            val caInput = assetManager.open(fileName)
//            val ca: Certificate
//            try {
//                ca = cf.generateCertificate(caInput)
//                Log.d("SslUtilsAndroid", "ca=" + (ca as X509Certificate).subjectDN)
//            } finally {
//                caInput.close()
//            }
//
//            val keyStoreType = KeyStore.getDefaultType()
//            keyStore = KeyStore.getInstance(keyStoreType)
//            keyStore!!.load(null, null)
//            keyStore.setCertificateEntry("ca", ca)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return keyStore
//    }
//
//    fun getTrustAllHostsSSLSocketFactory(): SSLSocketFactory? {
//        try {
//            // Create a trust manager that does not validate certificate chains
//            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager1 {
//
//                override fun getAcceptedIssuers(): Array<X509Certificate> {
//                    return arrayOf()
//
//                }
//
//                @Throws(CertificateException::class)
//                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
//                }
//
//                @Throws(CertificateException::class)
//                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
//                }
//            })
//
//            // Install the all-trusting trust manager
//            val sslContext = SSLContext.getInstance("SSL")
//            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
//            // Create an ssl socket factory with our all-trusting manager
//
//            return sslContext.socketFactory
//        } catch (e: KeyManagementException) {
//            e.printStackTrace()
//            return null
//        } catch (e: NoSuchAlgorithmException) {
//            e.printStackTrace()
//            return null
//        }
//
//    }
//}

class UnsafeOkHttpClient {
    companion object {
        fun getUnsafeOkHttpClient(context: Context): OkHttpClient {
            try {
                Log.i("MYTAG", "came here 7.0")

                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager1 {

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                        Log.i("MYTAG", "came here 7.1")
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                        Log.i("MYTAG", "came here 7.2")
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        Log.i("MYTAG", "came here 7.3")
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                Log.i("MYTAG", "came here 7.4")
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                Log.i("MYTAG", "came here 7.5")

                val builder = OkHttpClient.Builder()
                OkHttpClient.Builder(UnsafeOkHttpClient.getSslContextForCertificateFile(context, "my_certificate.pem").socketFactory)

                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager1)
                builder.hostnameVerifier(HostnameVerifier(function = { _, _ -> true }))
                Log.i("MYTAG", "came here 7.6")

                return builder.build()
            } catch (e: Exception) {
                Log.i("MYTAG", "came here 7.7")
                throw RuntimeException(e)
            }
        }


        fun getSslContextForCertificateFile(context: Context, fileName: String): SSLContext {
            try {
                val keyStore = UnsafeOkHttpClient.getKeyStore(context, fileName)
                val sslContext = SSLContext.getInstance("SSL")
                val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(keyStore)
                sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
                return sslContext
            } catch (e: Exception) {
                val msg = "Error during creating SslContext for certificate from assets"
                e.printStackTrace()
                throw RuntimeException(msg)
            }
        }

        private fun getKeyStore(context: Context, fileName: String): KeyStore? {
            var keyStore: KeyStore? = null
            try {
                val assetManager = context.assets
                val cf = CertificateFactory.getInstance("X.509")
                val caInput = assetManager.open(fileName)
                val ca: Certificate
                try {
                    ca = cf.generateCertificate(caInput)
                    Log.d("SslUtilsAndroid", "ca=" + (ca as X509Certificate).subjectDN)
                } finally {
                    caInput.close()
                }

                val keyStoreType = KeyStore.getDefaultType()
                keyStore = KeyStore.getInstance(keyStoreType)
                keyStore!!.load(null, null)
                keyStore.setCertificateEntry("ca", ca)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return keyStore
        }


    }
}

private fun OkHttpClient.Companion.Builder(okHttpClient: SSLSocketFactory?) {

}
