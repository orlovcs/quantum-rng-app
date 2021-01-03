package com.crimsonlabs.orlovcs.reaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class RetrieveAPI extends AsyncTask<Void, Void, String> {

    private RetrieveAPIHelper helper;
    private ProgressDialog progDailog;

    public RetrieveAPI(Context context, RetrieveAPIHelper helper) {
        this.helper = helper;
        this.progDailog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        super.onPreExecute();
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    protected String doInBackground(Void... urls) {
        String response;
        //try ANU Server
        try {
            String API_URL = "https://qrng.anu.edu.au/API/jsonI.php?length=40&type=uint16";
            URL url = new URL(API_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                response = stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }

        } catch (java.net.SocketTimeoutException e) {
            response = null;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            response = null;
        }

        return response;

    }

    protected void onPostExecute(String response) {
        //network call completed
        if (response != null) {
            // Convert String to json object
            JSONObject json;
            try {
                json = new JSONObject(response);
                JSONArray data_array;
                data_array = json.getJSONArray("data"); //<< get value here
                helper.onSuccess(data_array);

            } catch (JSONException e) {
                e.printStackTrace();
                //fail on error parsing json
                helper.onFail(null);
            }
        } else { //network call failed
            helper.onFail(null);
        }
        progDailog.dismiss();
    }
}