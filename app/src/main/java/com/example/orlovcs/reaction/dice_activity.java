package com.example.orlovcs.reaction;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


public class dice_activity extends AppCompatActivity implements OnItemClickListener, AdapterView.OnItemSelectedListener {

    TextView debug;
    TextView textOutput;
    String currOutput;
    ArrayList<Integer> nums;
    Integer rollOptionSelected = 0;
    Integer dieOptionSelected = 0;
    Boolean api = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.dice_layout);

        currOutput = "";

        Button generateButon = (Button) findViewById(R.id.generate_die);

        nums = new ArrayList<>();
        debug = (TextView) findViewById(R.id.textView5);
        textOutput = findViewById(R.id.dice_output);

        Spinner roll_spinner = findViewById(R.id.roll_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.rolls,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roll_spinner.setAdapter(adapter);
        roll_spinner.setOnItemSelectedListener(this);


        Spinner die_spinner = findViewById(R.id.die_spinner);
        ArrayAdapter<CharSequence> adapter_2 = ArrayAdapter.createFromResource(this,R.array.die,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        die_spinner.setAdapter(adapter_2);
        die_spinner.setOnItemSelectedListener(this);

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
        }else if (id == R.id.action_share){


            if (currOutput != null && currOutput != "") {

                Intent newi  = new sharefunc(currOutput).i;
                startActivity(Intent.createChooser(newi, "Share via"));

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

        if(adapterView.getId() == R.id.roll_spinner)
        {
            rollOptionSelected = i;
            Toast.makeText(getApplicationContext(),
                    "rolls: " + i,
                    Toast.LENGTH_SHORT).show();
        }  else  if(adapterView.getId() == R.id.die_spinner)
        {
            dieOptionSelected = i;
            Toast.makeText(getApplicationContext(),
                    "die: " + i,
                    Toast.LENGTH_SHORT).show();
        }







    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void setString(){

        if (!nums.isEmpty() && nums!=null) {

            Integer sides = 0;
            Integer max = 0;


            switch (dieOptionSelected){
                case 0: //649
                    max = 3;
                    break;
                case 1:
                    max = 4;
                    break;
                case 2:
                    max = 5;
                    break;
                case 3:
                    max = 6;
                    break;
                case 4:
                    max = 7;
                    break;
                case 5:
                    max = 8;
                    break;
                case 6:
                    max = 9;
                    break;
                case 7:
                    max = 10;
                    break;
                case 8:
                    max = 12;
                    break;
                case 9:
                    max = 14;
                    break;
                case 10:
                    max = 20;
                    break;
                case 11:
                    max = 24;
                    break;
                case 12:
                    max = 30;
                    break;
                case 13:
                    max = 48;
                    break;
                case 14:
                    max = 50;
                    break;
                case 15:
                    max = 100;
                    break;
                case 16:
                    max = 1000;
                    break;
                default:
                    max = 3;
                    break;
            }

            String output = "D"+max + "x"+rollOptionSelected;

            List<Integer> digitNums = nums.subList(0,21);


            Integer sum = 0;





            if (dieOptionSelected == -1){ //d1k

                Integer el = digitNums.get(0);
                debug.setText(String.valueOf(el));
                if (el < 99999){
                    el = el * 2;
                }
                output = String.valueOf(el%(99999+1));

            }else{



                for(int i = 0; i < rollOptionSelected+1;i++){

                    Integer v = digitNums.get(i)%(max) + 1;

                    if (i == 0){
                        output = String.valueOf(v);
                    }else{
                        output = output + "+" + v;
                    }
                    sum+=v;
                }

            }

            if (rollOptionSelected == 0){

                textOutput.setText(output);
                currOutput = output;

            }else{
                String tmp = output;
                output = "D"+ max + "x" + (rollOptionSelected+1) + ": " ;
                output += tmp;
                output +=" = "+sum;
                textOutput.setText(output);
                currOutput = output;
            }



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
            progDailog = new ProgressDialog(dice_activity.this);
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
                    urlConnection.setConnectTimeout(10000);

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