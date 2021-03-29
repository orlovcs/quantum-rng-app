package com.crimsonlabs.orlovcs.reaction;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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


public class coin_activity extends MainActivity implements OnItemClickListener, AdapterView.OnItemSelectedListener {

    TextView debug;
    TextView textOutput;
    String currOutput;
    ArrayList<Integer> nums;
    Integer coinAmountSelected = 0;
    Integer weight = 50;
    Boolean api = true;
    Boolean ANU = false;

    Integer weightcalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.coin_layout);

        currOutput = "";

        Button generateButon = (Button) findViewById(R.id.generate_coin);

        nums = new ArrayList<>();
        debug = (TextView) findViewById(R.id.textView5);
        textOutput = findViewById(R.id.coin_output);
        textOutput.setTextIsSelectable(true);

        final GenerationHelper generationHelper = new GenerationHelper();


        Spinner coin_spinner = findViewById(R.id.coin_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.rolls,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coin_spinner.setAdapter(adapter);
        coin_spinner.setOnItemSelectedListener(this);

        Spinner weight_spinner = findViewById(R.id.weight_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.weights,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight_spinner.setAdapter(adapter2);
        weight_spinner.setOnItemSelectedListener(this);

        final Context context = this;
        final RetrieveAPIHelper helper = new RetrieveAPIHelper() {
            @Override
            public void onSuccess(JSONArray data_array) {
                Toast.makeText(getApplicationContext(),
                        "API Call Success\nNumbers Generated",
                        Toast.LENGTH_SHORT).show();
                nums = generationHelper.processData(data_array);
                setString();
            }

            @Override
            public void onFail(String Response) {
                Toast.makeText(getApplicationContext(),
                        "API Call Failed\nManually Generated",
                        Toast.LENGTH_SHORT).show();
                nums = generationHelper.manualGeneration();
                setString();
            }
        };
        generateButon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (api == false){
                    Toast.makeText(getApplicationContext(),
                            "API Disabled\nManually Generated",
                            Toast.LENGTH_SHORT).show();
                    nums = generationHelper.manualGeneration();
                    setString();
                }else{
                    textOutput.setText("");
                    currOutput = "";
                    new RetrieveAPI(context, helper).execute();
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

            if (api){
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


        if(adapterView.getId() == R.id.coin_spinner)
        {
            coinAmountSelected = i;

        }else if(adapterView.getId() == R.id.weight_spinner)
        {

            switch (i) {
                case 0:
                    weight = 50;
                    break;
                case 1:
                    weight = 45;
                    break;
                case 2:
                    weight = 40;
                    break;
                case 3:
                    weight = 35;
                    break;
                case 4:
                    weight = 30;
                    break;
                case 5:
                    weight = 25;
                    break;
                case 6:
                    weight = 20;
                    break;
                case 7:
                    weight = 15;
                    break;
                case 8:
                    weight = 10;
                    break;
                case 9:
                    weight = 5;
                    break;
                default:
                    weight = 50;
            }

             weightcalc = (65535 * weight)/100;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    void setString(){

        if (!nums.isEmpty() && nums!=null) {




            String output = "";

            List<Integer> digitNums = nums.subList(0,21);
          // List<Integer> bonusNums = nums.subList(21,39);

            Integer tails = 0;
            Integer heads = 0;


                for(int i = 0; i < coinAmountSelected+1;i++){



                    Integer v = digitNums.get(i);
               //     debug.setText("weight is: " +weight + "cal: " + weightcalc + "v is " + v);


                    if (v  >   weightcalc){
                        heads++;
                        if (i == 0){
                            output = "H";
                        }else{
                            output = output + "," + "H";
                        }
                    }else {
                        tails++;
                        if (i == 0){
                            output = "T";
                        }else{
                            output = output + "," + "T";
                        }
                    } }

            textOutput.setText(output);
            currOutput = output;
            debug.setText("tails: " + tails + "\n" + "heads: " + heads );

        }}





}