package com.cpetsol.cpetsolutions.myaaptha.activity;

/**
 * Created by Admin on 6/13/2018.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by rajesh on 19/9/17.
 */

public class JsonParser {

    String TAG = "Json Parser";
    private HttpURLConnection conn;
    private DataOutputStream dataOutputStream;
    private StringBuilder result = new StringBuilder();
    private JSONObject jsonObject;

    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    public JSONObject post(String url, HashMap<String, String> params, HashMap<String, String> files) {

        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);//Allow Inputs
            conn.setDoOutput(true);//Allow Outputs
            conn.setUseCaches(false);//Don't use a cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(50000);
            if (files != null) {
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                for (String key : files.keySet()) {
                    conn.setRequestProperty(key, files.get(key));
                }
            }

            conn.connect();
            dataOutputStream = new DataOutputStream(conn.getOutputStream());

            //file uploading
            if (files != null) {
                for (String key : files.keySet()) {
                    int bytesRead, bytesAvailable, bufferSize;
                    byte[] buffer;
                    int maxBufferSize = 1 * 1024 * 1024;

                    File selectedFile = new File(files.get(key));
                    if (!selectedFile.isFile()) {
                        break;
                    }

                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    //writing bytes to data outputstream
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\";filename=\"" + files.get(key) + "\"" + lineEnd);

                    dataOutputStream.writeBytes(lineEnd);

                    FileInputStream fileInputStream = new FileInputStream(selectedFile);
                    //returns no. of bytes present in fileInputStream
                    bytesAvailable = fileInputStream.available();
                    //selecting the buffer size as minimum of available bytes or 1 MB
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    //setting the buffer as byte array of size of bufferSize
                    buffer = new byte[bufferSize];

                    //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                    while (bytesRead > 0) {
                        //write the bytes read from inputstream
                        dataOutputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    fileInputStream.close();
                }
            }
            //parameters writing when file uploading
            if (params != null && files != null) {
                for (String key : params.keySet()) {
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(params.get(key));
                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                }
            }
            //parameters writing when no file uploading
            else if (params != null) {
                StringBuilder psb = new StringBuilder();
                boolean flag = false;
                for (String key : params.keySet()) {
                    try {
                        if (flag) {
                            psb.append("&");
                        }
                        psb.append(key).append("=").append(URLEncoder.encode(params.get(key), "UTF-8"));

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    flag = true;
                }
                dataOutputStream.writeBytes(psb.toString());
            }

            Log.d(TAG, "RC: " + conn.getResponseCode() + " RM: " + conn.getResponseMessage());

            dataOutputStream.flush();
            dataOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //Receive the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(conn.getInputStream())));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d(TAG, "Result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jsonObject = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jsonObject;
    }

    public JSONObject get(String url, HashMap<String, String> params) {

        StringBuilder psb = new StringBuilder();
        boolean flag = false;
        for (String key : params.keySet()) {
            if (flag) {
                psb.append("&");
            }
            try {
                psb.append(key).append("=").append(URLEncoder.encode(params.get(key), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            flag = true;
        }

        if (psb.length() != 0) {
            url += "?" + psb.toString();
            Log.d(TAG, "url: " + url);
        }

        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoOutput(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setConnectTimeout(15000);
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //Receive the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(conn.getInputStream())));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d(TAG, "Result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jsonObject = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jsonObject;
    }
}
