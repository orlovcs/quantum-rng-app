package com.example.orlovcs.reaction;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
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

        Button iv = findViewById(R.id.button5);

        iv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), firstFrag.class);
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
                            , new firstFrag())
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


    class RetrieveAPI extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            debug.setText("");
        }

        protected String doInBackground(Void... urls) {

            try {
                String API_URL = "https://qrng.anu.edu.au/API/jsonI.php?length=6&type=uint8";
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
