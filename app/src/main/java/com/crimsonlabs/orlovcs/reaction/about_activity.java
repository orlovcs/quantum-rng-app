package com.crimsonlabs.orlovcs.reaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;


public class about_activity extends AppCompatActivity  {

    TextView debug;
    TextView link;
    String currOutput;
    ArrayList<Integer> nums;
    Integer coinAmountSelected = 0;
    Integer weight = 50;
    Boolean api = true;
    Integer weightcalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.about_layout);

        link = (TextView) findViewById(R.id.textView14);
        link.setMovementMethod(LinkMovementMethod.getInstance());

    }
}