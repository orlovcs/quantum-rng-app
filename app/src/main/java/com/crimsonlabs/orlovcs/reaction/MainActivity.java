package com.crimsonlabs.orlovcs.reaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    TextView debug;
    EditText firstLottoNum;
    TableRow num_table;
    ArrayList<Integer> nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.first_layout);

        Button lotteryMenuButton = findViewById(R.id.menu_649);
        Button passwordMenuButton = findViewById(R.id.menu_custom);
        Button diceMenuButton = findViewById(R.id.menu_dice);
        Button coinMenuButton = findViewById(R.id.menu_coin);
        Button numbersMenuButton = findViewById(R.id.menu_rng);
        Button themeMenuButton = findViewById(R.id.menu_theme);
        Button aboutMenuButton = findViewById(R.id.menu_about);

        lotteryMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), lottery_activity.class);
                startActivity(intent);
            }
        });

        diceMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), dice_activity.class);
                startActivity(intent);
            }

        });

        passwordMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), password_activity.class);
                startActivity(intent);
            }
        });

        coinMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), coin_activity.class);
                startActivity(intent);
            }
        });

        numbersMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), rng_activity.class);
                startActivity(intent);
            }
        });

        themeMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), theme_activity.class);
                startActivity(intent);
            }

        });

        aboutMenuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), about_activity.class);
                startActivity(intent);
            }
        });
    }

}
