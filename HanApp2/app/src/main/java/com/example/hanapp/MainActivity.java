package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.awt.font.NumericShaper;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText Username, Password;
    Button log_button;
    public String PasswordHolder="", UsernameHolder="";
    String finalResult;
    String HttpURL = "http://hanapp2021.000webhostapp.com/login.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hide the Action Bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //Initialize the fields
        Username = findViewById(R.id.login_username);
        Password = findViewById(R.id.login_password);
        log_button = findViewById(R.id.log_button);

        //if the login button was click
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                  UserLoginFunction(UsernameHolder, PasswordHolder);

                }
                else {
                    Toast.makeText(MainActivity.this, "Please fill all form fields.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void CheckEditTextIsEmptyOrNot(){

        UsernameHolder = Username.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        if(TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder))
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }
    //function to validate the username and password based on the data from the database
    public void UserLoginFunction(final String username, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(MainActivity.this,"Logging in...","Loading Please Wait");
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                String httpmessage = httpResponseMsg.trim();

                if(httpmessage.equals("Customer")){

                    SharedPreferences sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Username_login", UsernameHolder);
                    editor.putString("Password_login", PasswordHolder);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), Homepage.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(),"Congatulations you are already logged in...",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"as "+ UsernameHolder + "...",Toast.LENGTH_SHORT).show();


                }else if (httpmessage.equals("Establishment")){

                    Toast.makeText(MainActivity.this,"Invalid Username or Passwrod",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(MainActivity.this,httpmessage,Toast.LENGTH_LONG).show();
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

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(username,password);
    }

    //clickable textview for signup page
    public void signup (View view){
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }
}