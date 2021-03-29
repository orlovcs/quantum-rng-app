package com.crimsonlabs.orlovcs.reaction;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class rng_activity extends MainActivity implements OnItemClickListener, AdapterView.OnItemSelectedListener {

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

    Boolean ANU = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set Content View
        setContentView(R.layout.rng_layout);

        currOutput = "";

        Button generateButon = findViewById(R.id.generate_rng);

        nums = new ArrayList<>();
        debug = findViewById(R.id.textView5);
        textOutput = findViewById(R.id.rng_output);
        textOutput.setTextIsSelectable(true);

        setMin = findViewById(R.id.Small);
        setMax = findViewById(R.id.Big);

        String s = setMin.getText().toString();

        BigInteger sizeTest = new BigInteger(s);
        BigInteger intMax = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger intMin = BigInteger.valueOf(Integer.MIN_VALUE);

        if (sizeTest.compareTo(intMax) < 0 && sizeTest.compareTo(intMin) > 0) {
            minimum = Integer.parseInt(s);

        } else {
            maximum = 0;
            minimum = 0;
        }


        String b = setMax.getText().toString();
        sizeTest = new BigInteger(b);

        if (sizeTest.compareTo(intMax) < 0 && sizeTest.compareTo(intMin) > 0) {
            maximum = Integer.parseInt(b);

        } else {
            maximum = 0;
            minimum = 0;
        }


        reps = 1;

        dec = findViewById(R.id.Decimal);

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


                if (!api) {
                    nums = generationHelper.manualGeneration();
                    setString();
                } else {
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

            if (api) {
                api = false;
                Toast.makeText(getApplicationContext(),
                        "API Disabled",
                        Toast.LENGTH_SHORT).show();
                item.setChecked(false);
            } else {
                api = true;
                Toast.makeText(getApplicationContext(),
                        "API Enabled",
                        Toast.LENGTH_SHORT).show();
                item.setChecked(true);
            }

        } else if (id == R.id.action_copy) {

            if (currOutput != null && !currOutput.equals("")) {

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

    void setString() {

        if (!nums.isEmpty()) {


            real = dec.isChecked();


            String output = "";

            String s = setMin.getText().toString();
            BigInteger sizeTest = new BigInteger(s);
            BigInteger intMax = BigInteger.valueOf(Integer.MAX_VALUE);
            BigInteger intMin = BigInteger.valueOf(Integer.MIN_VALUE);

            if (sizeTest.compareTo(intMax) < 0 && sizeTest.compareTo(intMin) > 0) {
                minimum = Integer.parseInt(s);

            } else {
                maximum = 0;
                minimum = 0;
            }


            String b = setMax.getText().toString();
            sizeTest = new BigInteger(b);
            if (sizeTest.compareTo(intMax) < 0 && sizeTest.compareTo(intMin) > 0) {
                maximum = Integer.parseInt(b);

            } else {
                maximum = 0;
                minimum = 0;
            }


            List<Integer> digitNums = nums.subList(0, reps);
            Integer diff = maximum - minimum;
            if (minimum < maximum && minimum >= 0) {
                for (int i = 0; i < digitNums.size(); i++) {


                    Double rand = new Double(digitNums.get(i));
                    Double v = minimum + (diff) * rand / 65535;

                    int in = Integer.valueOf((int) Math.round(v));

                    if (real == true) {
                        output = output + '\n' + v;
                        currOutput = output;
                    } else {
                        output = output + '\n' + in;
                        currOutput = output;

                    }
                }
            } else {
                output = "Boundary error" + '\n' + "Make sure the numbers are all greater than 0 and less than 2147483647" + '\n' + "Also make sure that Maximum is larger than Minimum";
                currOutput = output;

            }
            textOutput.setText(output);
            currOutput = output;
        }
    }



}