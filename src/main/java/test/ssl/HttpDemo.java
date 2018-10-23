package test.ssl;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;

import javax.net.ssl.TrustManager;

import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import javax.net.ssl.HttpsURLConnection;

import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;

import javax.net.ssl.SSLSession;

import javax.security.cert.CertificateException;

public class HttpDemo {

	 private static final String TOMCAT_CERT="https://114.115.146.36:8443/"; 
	 private static final String SGITG_CERT="https://114.115.146.36:8943/";

	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

 
                 public boolean verify(String hostname, SSLSession session) {

					return true;

				}
    };
    public void testTomCat(){
		httpGet(TOMCAT_CERT);
	}	
	public void testSGITG(){
		httpGet(SGITG_CERT);
	}
	
 //TOMCAT_CERT
    public void httpGet(String urlPath){
		
		System.out.println("Connect url:"+urlPath);

    	StringBuffer tempStr = new StringBuffer();

    	String responseContent="";

       HttpURLConnection conn = null;

    	try {

            // Create a trust manager that does not validate certificate chains

            //trustAllHosts();

    		URL url = new URL(urlPath);

             HttpsURLConnection https = (HttpsURLConnection)url.openConnection();

			if (url.getProtocol().toLowerCase().equals("https")) {

				https.setHostnameVerifier(DO_NOT_VERIFY);

				conn = https;

			} else {

				conn = (HttpURLConnection)url.openConnection();

			}

            conn.connect();

            System.out.println(conn.getResponseCode() + " " + conn.getResponseMessage());
			

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch(Exception e){

            e.printStackTrace();

        }finally{
			try{
			conn.disconnect();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		

    }

    

    /**

 * Trust every server - dont check for any certificate

 */

private void trustAllHosts() {

 

	// Create a trust manager that does not validate certificate chains

	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

 

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {

			return new java.security.cert.X509Certificate[] {};

		}
		public void checkClientTrusted(X509Certificate[] chain, String authType)  {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

	} };

 

	// Install the all-trusting trust manager

	try {

		SSLContext sc = SSLContext.getInstance("TLS");

		sc.init(null, trustAllCerts, new java.security.SecureRandom());

		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	} catch (Exception e) {

		e.printStackTrace();

	}

}

	public static void main(String[] args) {
		HttpDemo demo = new HttpDemo();
		demo.testTomCat();
		
		demo.testSGITG();

	}

 

}
