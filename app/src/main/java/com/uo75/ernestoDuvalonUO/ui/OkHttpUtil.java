package com.uo75.ernestoDuvalonUO.ui;

import java.security.cert.CertificateException;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

import static java.util.logging.Logger.getLogger;

/*
okhttp version used 3.8.1 -> 4.9.0

Clase para salatar verificacion de certificados (Bajo propio riesgo si sabe que esta haciendo)
*/
public class OkHttpUtil {

    private static final Logger LOGGER = getLogger(OkHttpUtil.class.getName());

    private static OkHttpClient client = null;
    private static boolean ignoreSslCertificate = false;

    public static OkHttpClient getClient() {
        return client;
    }

    public static void init(boolean ignoreCertificate) throws Exception {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // LOGGER.info("Initialising httpUtil with default configuration");
        if (ignoreCertificate) {
            ignoreSslCertificate = true;
            builder = configureToIgnoreCertificate(builder);
        }

        //Other application specific configuration

        client = builder.build();
    }

    //Setting testMode configuration. If set as testMode, the connection will skip certification check
    private static OkHttpClient.Builder configureToIgnoreCertificate(OkHttpClient.Builder builder) {
        // LOGGER.warning("Ignore Ssl Certificate");
        try {

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            // LOGGER.warning("Exception while configuring IgnoreSslCertificate" + e);
        }
        return builder;
    }

}