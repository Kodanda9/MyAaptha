package com.cpetsol.cpetsolutions.myaaptha.helper;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by Kodanda.D on 9/1/2017.
 */

public class AsyncTaskHelper {
    public static String makeServiceCall(String url, String method, JSONObject post_object) {
        String server_responce=null;
        if(method.equalsIgnoreCase("GET"))
        {
            try
            {
                url = url.replaceAll(" ", "%20");
                server_responce =downloadContent(url);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(method.equalsIgnoreCase("POST")) {
            BufferedReader reader = null;
            String Error = null;
            try {

                // Defined URL  where to send data
                url = url.replaceAll(" ", "%20");
                java.net.URL request_url = new java.net.URL(url);
                HttpURLConnection conn = (HttpURLConnection) request_url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-type", "application/json");
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                String json = post_object.toString();
                wr.write(json);
                wr.flush();
                int code = conn.getResponseCode();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "");
                }

                // Append Server Response To Content String
                server_responce = sb.toString();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return server_responce;
    }

    private static String downloadContent(String myurl) throws IOException {
        InputStream is = null;
        // int length = 500;

        try {
            java.net.URL url = new java.net.URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = convertInputStreamToString(is);
            return contentAsString;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static String convertInputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
