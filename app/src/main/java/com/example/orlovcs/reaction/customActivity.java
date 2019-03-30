package com.example.orlovcs.reaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class customActivity extends AppCompatActivity
{

    TextView debug;
    EditText firstLottoNum;
    TableRow num_table;
    ArrayList<Integer> nums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.lottery_layout);


        Button generateButon  = (Button) findViewById(R.id.generate);

        nums = new ArrayList<>();
        debug = (TextView) findViewById(R.id.textView2);
        num_table = (TableRow) findViewById(R.id.num_table);




        generateButon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RetrieveAPI().execute();
            }
        });


    }


    class RetrieveAPI extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            debug.setText("");
        }

        protected String doInBackground(Void... urls) {

            try {
                String API_URL = "https://qrng.anu.edu.au/API/jsonI.php?length=7&type=uint8";
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        protected void onPostExecute(String response) {
            if(response == null) {
                response = "ERROR";
            }

            Log.i("INFO", response);
            debug.setText(response);

            // Convert String to json object
            JSONObject json = null;
            try {

                json = new JSONObject(response);
                JSONArray data_array = json.getJSONArray("data"); //<< get value here

                debug.setText(String.valueOf(data_array.get(0)));

                for(int i=0;i<data_array.length();i++){
                    int data = data_array.getInt(i);
                    debug.setText(  debug.getText() + "\n" + i + " is " + String.valueOf(data) + ". num mod 49 is " + data%49  );
                    // firstLottoNum.setText(String.valueOf(data));

                    EditText num = (EditText) num_table.getChildAt(i);

                    num.setText(String.valueOf(data % 49));

                    nums.add(data%49);

                }





            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}