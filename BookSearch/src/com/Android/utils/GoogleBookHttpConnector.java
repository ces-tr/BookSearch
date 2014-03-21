package com.Android.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

public class GoogleBookHttpConnector  {
	
		public static JSONObject httpsGetRequest(String uri) throws IOException, JSONException {
            URL url = new URL(uri);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                    InputStream in = new BufferedInputStream(
                                    urlConnection.getInputStream());
                    return readStream(in);
            }catch(IOException e){
                    throw e;
            } finally {
                    urlConnection.disconnect();
            }
		}

		public static JSONObject httpGetRequest(String uri) throws IOException, JSONException {
            URL url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                    InputStream in = new BufferedInputStream(
                                    urlConnection.getInputStream());
                    return readStream(in);
            }catch(IOException e){
                    throw e;
            } finally {
                    urlConnection.disconnect();
            }
		}

		
		 private static JSONObject readStream(InputStream inputStream) throws IOException, JSONException {
             
			 BufferedReader r = new BufferedReader(
                             new InputStreamReader(inputStream));
             StringBuilder total = new StringBuilder();
             String line;
             while ((line = r.readLine()) != null) {
                     total.append(line);
             }
             return new JSONObject(total.toString());
		 }

		
}
