package com.example.orlovcs.reaction;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class rng_activity extends AppCompatActivity implements OnItemClickListener, AdapterView.OnItemSelectedListener {

    TextView debug;
    TextView textOutput;
    String currOutput;
    ArrayList<Integer> nums;
    Integer minimum;
    Integer maximum;
    Integer reps;
    Boolean api = true;
    EditText setMin;
    EditText setMax;
    SeekBar repSet;
    Boolean real;
    CheckBox dec;
    TextView amountnum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        // Set Content View
        setContentView(R.layout.rng_layout);

        currOutput = "";

        Button generateButon = (Button) findViewById(R.id.generate_rng);

        nums = new ArrayList<>();
        debug = (TextView) findViewById(R.id.textView5);
        textOutput = findViewById(R.id.rng_output);

        setMin = (EditText) findViewById(R.id.Small);
        setMax = (EditText) findViewById(R.id.Big);

        String s = setMin.getText().toString();
        minimum = Integer.parseInt(s);


        String b = setMax.getText().toString();
        maximum = Integer.parseInt(b);

        reps = 1;

        dec =  findViewById(R.id.Decimal);

        repSet = findViewById(R.id.numberLine);

        amountnum = findViewById(R.id.amount_num);

        repSet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                amountnum.setText(String.valueOf(progress + 1));
                reps = progress + 1;
                // Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();
            }
        });


        generateButon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (api == false){
                    Toast.makeText(getApplicationContext(),
                            "API Disabled\nManually Generated",
                            Toast.LENGTH_SHORT).show();
                    manualGeneration();
                    setString();
                }else{
                    textOutput.setText("");
                    currOutput = "";
                    new RetrieveAPI().execute();
                }
            }
        });
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_api) {

            if (api == true){
                api = false;
                Toast.makeText(getApplicationContext(),
                        "API Disabled",
                        Toast.LENGTH_SHORT).show();
                item.setChecked(false);
            }else{
                api = true;
                Toast.makeText(getApplicationContext(),
                        "API Enabled",
                        Toast.LENGTH_SHORT).show();
                item.setChecked(true);
            }

        }else if (id == R.id.action_copy){

            if (currOutput != null && currOutput != "") {

                final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Source Text", currOutput);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(getApplicationContext(),
                        "Copied!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {









    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void setString(){

        if (!nums.isEmpty() && nums!=null) {




            if (dec.isChecked()) {
                real = true;
            }
            else{
                real = false;
            }


            String output = "";

            String s = setMin.getText().toString();
            minimum = Integer.parseInt(s);




            String b = setMax.getText().toString();
            maximum = Integer.parseInt(b);


            List<Integer> digitNums = nums.subList(0,reps);
            Integer diff =  maximum - minimum;
            debug.setText(' ' + String.valueOf(diff) + ' ' + String.valueOf(minimum) + ' ' + String.valueOf(maximum));


            if (minimum < maximum && minimum >= 0){
            for(int i = 0; i < digitNums.size(); i++) {


                Double rand = new Double(digitNums.get(i));
                Double v = minimum + (diff) * rand / 65535;

                int in = Integer.valueOf((int) Math.round(v));

             //   Toast.makeText(this, " " + in, Toast.LENGTH_SHORT).show();

                if (real == true) {
                    output = output + '\n' + String.valueOf(v);
                } else {


                    output = output + '\n' + String.valueOf(in);
                }
            }


            }else{
                output = "Boundary error";
            }








            textOutput.setText(output);

        }}




    void manualGeneration(){
        nums = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 40; i++){
            int n = rand.nextInt(65535); //ANU Bound
            nums.add(n);
        }
    }


    void processData(JSONArray data_array){
        nums = new ArrayList<>();
        try {
            if (data_array != null){
            for(int i=0;i<data_array.length();i++){
                int data = data_array.getInt(i);
                debug.setText(  debug.getText() + "\n" + i + " is " + String.valueOf(data) + ". num mod 49 is " + data%49  );
                // firstLottoNum.setText(String.valueOf(data));
                nums.add(data);
            }}


        } catch (JSONException e) {
            e.printStackTrace();
        }



        //  Toast.makeText(this, "Generated", Toast.LENGTH_SHORT).show();

    }


    class RetrieveAPI extends AsyncTask<Void, Void, String> {

        private Exception exception;
        private ProgressDialog progDailog;

        protected void onPreExecute() {
            debug.setText("");
            super.onPreExecute();
            progDailog = new ProgressDialog(rng_activity.this);
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        protected String doInBackground(Void... urls) {
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
                        return stringBuilder.toString();
                    } finally {
                        urlConnection.disconnect();
                    }

            }catch (java.net.SocketTimeoutException e) {
                return null;
            }
            catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
        protected void onPostExecute(String response) {

            if (response == null) {
                response = "ERROR";

                Toast.makeText(getApplicationContext(),
                        "API Call Failed\nManually Generated",
                        Toast.LENGTH_SHORT).show();
                manualGeneration();
                setString();

            } else {

                Log.i("INFO", response);
                debug.setText(response);

                // Convert String to json object
                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    JSONArray data_array = json.getJSONArray("data"); //<< get value here
                    processData(data_array);
                    setString();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(),
                        "API Call Success\nNumbers Generated",
                        Toast.LENGTH_SHORT).show();
            }
            progDailog.dismiss();



        }
    }

}
