package com.crimsonlabs.orlovcs.reaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView debug;
    EditText firstLottoNum;
    TableRow num_table;
    ArrayList<Integer> nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.first_layout);

        Button six = findViewById(R.id.menu_649);
        Button pswd = findViewById(R.id.menu_custom);
        Button dice = findViewById(R.id.menu_dice);

        Button coin = findViewById(R.id.menu_coin);
        Button rng = findViewById(R.id.menu_rng);

        Button about = findViewById(R.id.menu_about);

        six.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), lottery_activity.class);
                startActivity(intent);
            }

        });

        dice.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), dice_activity.class);
                startActivity(intent);
            }

        });

        pswd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), password_activity.class);
                startActivity(intent);
            }

        });


        coin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), coin_activity.class);
                startActivity(intent);
            }

        });

        rng.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), rng_activity.class);
                startActivity(intent);
            }

        });

        about.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), about_activity.class);
                startActivity(intent);
            }

        });
    }

}
