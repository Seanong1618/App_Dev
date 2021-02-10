package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class home2 extends AppCompatActivity {
    String Username_home2= "", Password_home2 = "";
    ProgressDialog progressDialog;
    String HttpURL = "http://hanapp2021.000webhostapp.com/json_parsing.php";
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    //for storage
    RecyclerView recyclerView;
    List<customer_details> customerDetails;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        //for storage section
        recyclerView = findViewById(R.id.recycler);
        customerDetails = new ArrayList<>();
        //-------------------//
        //Hide the Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        actionBar.setDisplayHomeAsUpEnabled(true);
        //from QrScanner class
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        Username_home2 = sharedPreferences.getString("Username_login_Est","");
        Password_home2 = sharedPreferences.getString("Password_login_Est","");

        UserRegisterFunction(Username_home2,Password_home2);


    }

    public void UserRegisterFunction(final String Username_home2, final String Password_home2) {

        class UserRegisterFunctionClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(home2.this, "", "Loading Please Wait");
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                //parsing
                try {
                    JSONArray jsonArray = new JSONArray(httpResponseMsg);
                    //loop to get all the records
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.i(home2.class.getName(), jsonObject.getString("firstname"));
                        Log.i(home2.class.getName(), jsonObject.getString("middlename"));
                        Log.i(home2.class.getName(), jsonObject.getString("lastname"));
                        Log.i(home2.class.getName(), jsonObject.getString("contactnumber"));
                        Log.i(home2.class.getName(), jsonObject.getString("temperature"));
                        Log.i(home2.class.getName(), jsonObject.getString("date_time"));
                        Log.i(home2.class.getName(), jsonObject.getString("address"));
                        //put it in the customer details
                        customer_details customerDetails1 = new customer_details();
                        customerDetails1.setName("Name: "+jsonObject.getString("lastname").toString()+", "+jsonObject.getString("firstname")+", "+jsonObject.getString("middlename"));
                        customerDetails1.setContact("Contact #: "+jsonObject.getString("contactnumber"));
                        customerDetails1.setTemperature("Temperature: "+jsonObject.getString("temperature"));
                        customerDetails1.setAddress("Address: "+jsonObject.getString("address"));
                        customerDetails1.setDatetime("Date_Time of Entry: "+jsonObject.getString("date_time"));

                        customerDetails.add(customerDetails1);

                    }
                } catch (JSONException e) {
                    Log.d("addItemsFromJSON: ", String.valueOf(e));
                }
                adapter = new Adapter(getApplication(),customerDetails);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("username", params[0]);
                hashMap.put("password", params[1]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(Username_home2, Password_home2);

    }

}