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
import android.widget.Button;
import android.widget.SeekBar;
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
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class password_activity extends AppCompatActivity implements OnItemClickListener, AdapterView.OnItemSelectedListener {

    TextView debug;
    TextView textOutput;
    String currOutput;
    ArrayList<Integer> nums;
    Integer rollOptionSelected = 0;
    Integer dieOptionSelected = 0;
    Boolean api = true;
    Boolean ANU = false;


    TextView lengthnum, uppercasenum, digitsnum, symbolsnum;
    SeekBar lengthseek, uppercaseseek, digitsseek, symbolsseek;

    Integer passlength = 0;
    Integer passupper = 0;
    Integer passdigits = 0;
    Integer passsymbols = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.password_layout);

        currOutput = "";

        Button generateButon = (Button) findViewById(R.id.generate_password);

        nums = new ArrayList<>();
        debug = (TextView) findViewById(R.id.textView5);
        textOutput = findViewById(R.id.password_output);
        textOutput.setTextIsSelectable(true);

        lengthnum = findViewById(R.id.length_num);
        uppercasenum = findViewById(R.id.length_uppercase);
        digitsnum = findViewById(R.id.length_digits);
        symbolsnum = findViewById(R.id.length_symbols);

        lengthseek = findViewById(R.id.length_seek);
        uppercaseseek = findViewById(R.id.uppercase_seek);
        digitsseek = findViewById(R.id.digits_seek);
        symbolsseek = findViewById(R.id.symbols_seek);



        lengthseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

                lengthnum.setText(String.valueOf(progress));
                uppercaseseek.setMax(progress);
                passlength = progress;

            }
        });


        uppercaseseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                uppercasenum.setText(String.valueOf(progress));


                digitsseek.setMax(lengthseek.getProgress() - progress);
                passupper = progress;
                // symbolsseek.setMax(progress);
                // Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();
            }
        });

        digitsseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                digitsnum.setText(String.valueOf(progress));
                symbolsseek.setMax(lengthseek.getProgress() - uppercaseseek.getProgress() - progress);
                passdigits = progress;
                // Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();
            }
        });

        symbolsseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                symbolsnum.setText(String.valueOf(progress));
                passsymbols = progress;
                // Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();
            }
        });

        final GenerationHelper generationHelper = new GenerationHelper();


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

                if (passlength == 0){
                    Toast.makeText(getApplicationContext(),
                            "Increase password length!",
                            Toast.LENGTH_SHORT).show();
                }
                else if (api == false){
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




            String output = "";

            List<Integer> digitNums = nums.subList(0,30);


            Integer sum = 0;

            char[] alpha = new char[26];
            for(int i = 0; i < 26; i++){
                alpha[i] = (char)(97 + i);
            }

            char[] upperalpha = new char[26];
            for(int i = 0; i < 26; i++){
                upperalpha[i] = (char)(65 + i);
            }

            Integer[] digits = new Integer[10];
            for(int i = 0; i < 10; i++){
                digits[i] = i;
            }

            char[] symbols = new char[14];
            for(int i = 0; i < 14; i++){
                symbols[i] = (char)(33 + i);
            }





            //
            //  List<Character> out = new ArrayList<>();
            String working = "";

            char[] rand_lower = new char[passlength];
            for (int i = 0; i < passlength; i++){
                rand_lower[i] = (alpha[(nums.get(i) % 26)]);
                //  working += rand_lower[i];
            }

            char[] rand_upper = new char[passupper];
            for (int i = 0; i < passupper; i++){
                rand_upper[i] = upperalpha[(nums.get(26+i) % 26)];
                working += String.valueOf(rand_upper[i]);
            }

            Integer[] rand_digits = new Integer[passdigits];
            for (int i = 0; i < passdigits; i++){
                rand_digits[i] = digits[(nums.get(60+i) % 10)];
                working += String.valueOf(rand_digits[i]);
            }

            char[] rand_symbols = new char[passsymbols];
            for (int i = 0; i < passsymbols; i++){
                rand_symbols[i] = symbols[(nums.get(88-i) % 14)];
                working += String.valueOf(rand_symbols[i]);
            }

            Integer lowercaseTrimSize = passlength - passupper - passdigits - passsymbols;
            for (int i = 0; i < lowercaseTrimSize; i++){
                working += String.valueOf(rand_lower[i]);
            }
            debug.setText(working);
            List<Character> s = new ArrayList<>();
            for (int i = 0; i < working.length(); i++){
                s.add(working.charAt(i));
            }
            Collections.shuffle(s);
            working = "";
            for (int i = 0; i < s.size(); i++){
                working += s.get(i);
            }


            //    working = working.substring(lowercaseTrimSize, working.length());

            //  for (int i = 0; i < out.length; i++){
            //   working += out[i];
            // }



            // debug.setText(String.valueOf(alpha[0])+String.valueOf(alpha[26])+String.valueOf(upperalpha[0])+String.valueOf(upperalpha[26])+String.valueOf(digits[0])+String.valueOf(digits[9])+String.valueOf(symbols[0])+String.valueOf(symbols[14]));
            //  debug.setText(String.valueOf(symbols[13]));


            textOutput.setText(working);
            currOutput = working;

        }}




}