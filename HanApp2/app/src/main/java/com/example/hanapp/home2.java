package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class home2 extends AppCompatActivity {
    String Username_home2= "", Password_home2 = "";
    ProgressDialog progressDialog;
    String HttpURL = "http://hanapp2021.000webhostapp.com/json_parsing.php";
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String firstname,middlename,lastname,contactnumber,temp,date_time;
    TextView name;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        //Hide the Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Home");
        name = findViewById(R.id.name);
        //from QrScanner class
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        Username_home2 = sharedPreferences.getString("Username_login_Est","");
        Password_home2 = sharedPreferences.getString("Password_login_Est","");
        //pass the value to the function
        show = findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Username_home2.isEmpty() || Password_home2.isEmpty()){
                    Toast.makeText(home2.this, "Empty...", Toast.LENGTH_SHORT).show();
                }else{
                    UserRegisterFunction(Username_home2,Password_home2);
                }
            }
        });

    }

    public void UserRegisterFunction(final String Username_home2, final String Password_home2){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(home2.this, "", "Loading Please Wait");
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

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
                        //store the data
                        firstname= jsonObject.getString("firstname");
                        middlename= jsonObject.getString("middlename");
                        lastname= jsonObject.getString("lastname");
                        name.setText("Name: "+lastname+", "+firstname+", "+middlename);
                    }




                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("username",params[0]);
                hashMap.put("password",params[1]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(Username_home2,Password_home2);
    }

}