package com.floorat;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class BuildingApiTask extends AsyncTask<Void, Void, JSONArray> {

    private String url;
    static String[] name;
    static int len;

    public BuildingApiTask() {
    }

    public BuildingApiTask(String url) {
        this.url = url;
    }

    @Override
    protected JSONArray doInBackground(Void... para) {
        JSONArray result;
        try {
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            //----> put query parameter
            params.put("action", "email");
            //<----


            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(
                        String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            URL httpurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpurl
                    .openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length",
                    String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            result = new JSONArray(response.toString());
            //result = response.;
            //     result = response.toString();
        } catch (Exception e) {
            result = new JSONArray();
        }
        return result;
    }


    @Override
    protected void onPostExecute(JSONArray jsonArray) {

        name = new String[jsonArray.length()];
        len = jsonArray.length();

        System.out.println("String jo aai hai " + jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                name[i] = jsonArray.getString(i);
            }
            catch (JSONException e) {
            }
        }
    }
}