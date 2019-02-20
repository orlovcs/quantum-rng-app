package com.example.orlovcs.reaction;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView debug;
    EditText firstLottoNum;
    TableRow num_table;
    ArrayList<Integer> nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.first_layout);

        Button six = findViewById(R.id.menu_649);
        Button max = findViewById(R.id.menu_max);

        Button custom = findViewById(R.id.menu_custom);
        Button dice = findViewById(R.id.menu_dice);

        Button coin = findViewById(R.id.menu_coin);
        Button rng = findViewById(R.id.menu_rng);

        six.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), lottery_activity.class);
               // intent.putExtra("address", finalD);
             //   intent.putExtra("rating", mModel.ratings[finalD]);
                // Start activity
                startActivity(intent);
            }

        });

        max.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), secondFrag.class);
                // intent.putExtra("address", finalD);
                //   intent.putExtra("rating", mModel.ratings[finalD]);
                // Start activity
                startActivity(intent);
            }

        });

        dice.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), lottery_activity.class);
                // intent.putExtra("address", finalD);
                //   intent.putExtra("rating", mModel.ratings[finalD]);
                // Start activity
                startActivity(intent);
            }

        });

        custom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), customActivity.class);
                // intent.putExtra("address", finalD);
                //   intent.putExtra("rating", mModel.ratings[finalD]);
                // Start activity
                startActivity(intent);
            }

        });


        coin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), lottery_activity.class);
                // intent.putExtra("address", finalD);
                //   intent.putExtra("rating", mModel.ratings[finalD]);
                // Start activity
                startActivity(intent);
            }

        });

        rng.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), secondFrag.class);
                // intent.putExtra("address", finalD);
                //   intent.putExtra("rating", mModel.ratings[finalD]);
                // Start activity
                startActivity(intent);
            }

        });



        /*

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button generateButon  = (Button) findViewById(R.id.generate);

        nums = new ArrayList<>();
        debug = (TextView) findViewById(R.id.textView2);
        num_table = (TableRow) findViewById(R.id.num_table);




        generateButon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RetrieveAPI().execute();
            }
        });



        */


        //cant decode it here since its async

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.action_settings) {
            return true;
        }else if (id ==  R.id.action_copy){

            String source = "";

            for(int i=0;i<nums.size();i++){

               source = source + " " + String.valueOf(nums.get(i));

            }

            final android.content.ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Source Text", source);
            clipboardManager.setPrimaryClip(clipData);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        android.support.v4.app.FragmentManager fman = getSupportFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_first_layout) {
            Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();
/*
            fman.beginTransaction()
                    .replace(R.id.drawer_layout
                            , new lottery_activity())
                    .commit();


        }else if (id == R.id.nav_second_layout){
            fman.beginTransaction()
                    .replace(R.id.drawer_layout
                            , new secondFrag())
                    .commit();
        }


*/
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}
