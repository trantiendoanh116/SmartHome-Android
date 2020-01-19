package com.iot.smarthome.network;

import android.util.Log;

import com.iot.smarthome.AppConfig;

import org.apache.http.client.utils.URIBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpConnectionClient {
    private static final String TAG = "HttpConnectionClient";

    // HTTP POST request
    @SafeVarargs
    public static HttpURLConnection post(String uri, Map<String, String> pamrams, String body, Map<String, String>... headers) {
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            for (Map.Entry<String, String> entry : pamrams.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                uriBuilder.addParameter(name, value);
            }

            URL url = uriBuilder.build().toURL();
            // 1. create CustomHttpURLConnection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            //add request header
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            if (headers.length > 0) {
                for (Map.Entry<String, String> entry : headers[0].entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    con.setRequestProperty(name, value);

                }
            }

            //set more settings
            con.setConnectTimeout(AppConfig.CONNECTION_TINMEOUT);
            con.setReadTimeout(AppConfig.CONNECTION_TINMEOUT);
            con.setDoOutput(true);
            // add JSON content to POST request body
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(body);
            writer.flush();
            writer.close();
            os.close();
            // make POST request to the given URL
            //conn.connect();
            return con;
        } catch (Exception e) {
            Log.e(TAG, String.format("[%s]: %s", "sendPost", e));
            return null;
        }

    }

    // HTTP GET request
    @SafeVarargs
    public static HttpURLConnection get(String uri, Map<String, String> pamrams, Map<String, String>... headers) {
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            for (Map.Entry<String, String> entry : pamrams.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                uriBuilder.addParameter(name, value);
            }

            URL url = uriBuilder.build().toURL();
            //Log.d(TAG, "url: " + url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            if (headers.length > 0) {
                for (Map.Entry<String, String> entry : headers[0].entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    con.setRequestProperty(name, value);

                }
            }

            //set more settings
            con.setConnectTimeout(AppConfig.CONNECTION_TINMEOUT);
            con.setReadTimeout(AppConfig.CONNECTION_TINMEOUT);
            //int responseCode = con.getResponseCode();
            // Log.d(TAG, "response status: " + responseCode);
            return con;
            //debug result
        } catch (Exception e) {
            Log.e(TAG, String.format("[%s]: %s", "sendGet", e.getMessage()));
            return null;
        }

    }

    // HTTP POST request
    @SafeVarargs
    public static HttpURLConnection delete(String uri, Map<String, String> pamrams, String body, Map<String, String>... headers) {
        try {
            URIBuilder uriBuilder = new URIBuilder(uri);
            for (Map.Entry<String, String> entry : pamrams.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                uriBuilder.addParameter(name, value);
            }

            URL url = uriBuilder.build().toURL();
            // 1. create CustomHttpURLConnection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            //add request header
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            if (headers.length > 0) {
                for (Map.Entry<String, String> entry : headers[0].entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    con.setRequestProperty(name, value);

                }
            }
            //set more settings
            con.setConnectTimeout(AppConfig.CONNECTION_TINMEOUT);
            con.setReadTimeout(AppConfig.CONNECTION_TINMEOUT);
            con.setDoOutput(true);
            // add JSON content to POST request body
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(body);
            writer.flush();
            writer.close();
            os.close();
            // make POST request to the given URL
            //conn.connect();
            return con;
        } catch (Exception e) {
            Log.e(TAG, String.format("[%s]: %s", "sendPost", e));
            return null;
        }

    }


    public static String getResponse(HttpURLConnection con) {
        try {
            if (con != null) {
                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, String.format("[%s]: %s", "getResponse", e.getMessage()));

        }
        return null;
    }
}
