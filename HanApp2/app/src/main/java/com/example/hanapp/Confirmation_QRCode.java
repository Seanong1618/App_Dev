package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Confirmation_QRCode extends AppCompatActivity {
    String temp_holder = "", Data = "";
    String Username_home1 = "" , Password_home1 = "";
    String sessionId = "";
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String HttpURL = "http://hanapp2021.000webhostapp.com/Entry_details.php";

    Button proceed,cancel;
    TextView estname,address,date_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation__q_r_code);
        //action button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Confirmation");
        actionBar.setDisplayHomeAsUpEnabled(true);

        proceed = findViewById(R.id.proceed);
        cancel = findViewById(R.id.cancel);
        estname = findViewById(R.id.EstName);
        address = findViewById(R.id.Address);
        date_time = findViewById(R.id.Date_time);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        Username_home1 = sharedPreferences.getString("Username","");
        Password_home1 = sharedPreferences.getString("Password","");
        Data = sharedPreferences.getString("Data", "");
        temp_holder = sharedPreferences.getString("Temperature", "");
        //set all the data into the tectview
        //parsing

        sessionId = sharedPreferences.getString("json_parse", "");
        try {
            JSONArray jsonArray = new JSONArray(sessionId);
            //loop to get all the records
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String EstName = jsonObject.getString("EstablishmentName");
                String EstAddress = jsonObject.getString("EstablishmentAddress");
                estname.setText(EstName);
                address.setText(EstAddress);
            }
        } catch (JSONException e) {
            Log.d("addItemsFromJSON: ", String.valueOf(e));
        }

        Date d1 = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
        String formattedDate = df.format(d1);
        date_time.setText(formattedDate);
        //if the continue button is pressed
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //proceed to saving of data
                if (!Username_home1.isEmpty() || !Password_home1.isEmpty() || !Data.isEmpty() || !temp_holder.isEmpty()){
                    UserRegisterFunction(temp_holder,Data,Username_home1,Password_home1);
                }else {
                    Toast.makeText(Confirmation_QRCode.this, "Empty...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //if cancel is pressed
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it will go back to the scanner page
                Intent intent = new Intent(getApplicationContext(), QrScanner.class);
                startActivity(intent);
            }
        });


    }
    public void UserRegisterFunction(final String temperature_holder, final String Data, final String Username_home1, final String Password_home1){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Confirmation_QRCode.this, "", "Loading Please Wait");
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                if (httpResponseMsg.equalsIgnoreCase("Invalid Establishment ID")){

                    Toast.makeText(Confirmation_QRCode.this, "Invalid Establishment ID...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Confirmation_QRCode.this, "Establishment ID is not Registered...", Toast.LENGTH_SHORT).show();

                }else if (httpResponseMsg.equalsIgnoreCase("Data Insertion Success")){
                    Intent intent = new Intent(getApplicationContext(), QrScanner.class);
                    startActivity(intent);
                    Toast.makeText(Confirmation_QRCode.this, "Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Confirmation_QRCode.this, httpResponseMsg, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("temperature",params[0]);
                hashMap.put("Data",params[1]);
                hashMap.put("Username_home1",params[2]);
                hashMap.put("Password_home1",params[3]);



                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(temperature_holder,Data,Username_home1,Password_home1);
    }
}