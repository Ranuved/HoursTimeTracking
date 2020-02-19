package com.works.admin.hours_timetracking;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by TreeSoft on 11/18/2015.
 */
public class Jsonparsor {
    static InputStream is = null;
    URL url_request;

    public String getJSONFromUrl(String url, Map<String, String> parameter) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder params = new StringBuilder("");
        String result = null;
        try {
            url_request = new URL(url);
            for (String s : parameter.keySet()) {
                params.append("&" + s + "=");
                params.append(URLEncoder.encode(parameter.get(s), "UTF-8"));
               // Log.e("param", params.toString());
            }

            HttpURLConnection con = (HttpURLConnection) url_request.openConnection();
            con.setRequestMethod("POST");
            //  con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "UTF-8");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
            outputStreamWriter.write(params.toString());
            outputStreamWriter.flush();

            int responseCode = con.getResponseCode();
          //  Log.e("responce", Integer.toString(responseCode));
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            if (in != null)
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine + "\n");
                }
            in.close();

            result = response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
