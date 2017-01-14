package itea.forecast;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by shevc on 12.01.2017.
 */

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();
    private static HttpHandler httpHandler = null;

    private HttpHandler() {
    }

    public static HttpHandler getInstance(){
        if (httpHandler == null){
            httpHandler = new HttpHandler();
        }
        return httpHandler;
    }


    public String SendRequestToUrl(int regionId) {
        String response = null;
        try {
            URL obj = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%3D" + regionId + "%20and%20u='c'&format=json&diagnostics=true&callback=");
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Android/5");
            int responseCode = connection.getResponseCode();
            Log.d(TAG, "Starting a connection...");
            Log.d(TAG, "Response Code is: " + responseCode);
            if (responseCode == 400){
                Log.d(TAG, "Ooops, something is wrong with query...");
            }
            InputStream in = new BufferedInputStream(connection.getInputStream());
            response = ConvertInputStreamToString(in);
            Log.d("RESPONSE", response.toString());
        } catch (ProtocolException e) {
            Log.e(TAG, e.getMessage());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return response;
    }

    private String ConvertInputStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String inputLine;
        StringBuffer response = new StringBuffer();
        try {
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return response.toString();
    }
}
