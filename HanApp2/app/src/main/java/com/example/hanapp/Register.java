package com.example.hanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Register extends AppCompatActivity {
    String username="",password="",confirmpassword="",firstname="",middlename="",lastname=""
            ,address="",contactnumber="",sex="",age="";
    EditText signup_username, signup_password,signup_confirmpassword,signup_firtname,signup_middlename,
            signup_lastname,signup_address,signup_contactnumber,signup_sex,signup_age;
    String finalResult ;
    String HttpURL = "http://hanapp2021.000webhostapp.com/customer_signup.php";
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Customer Signup");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initialize
        signup_username = findViewById(R.id.signup_username);
        signup_password = findViewById(R.id.signup_password);
        signup_confirmpassword = findViewById(R.id.signup_confirmpassword);
        signup_firtname =  findViewById(R.id.signup_firstname);
        signup_middlename =  findViewById(R.id.signup_middlename);
        signup_lastname =  findViewById(R.id.signup_lastname);
        signup_address =  findViewById(R.id.signup_address);
        signup_contactnumber =  findViewById(R.id.signup_contactnumber);
        signup_sex =  findViewById(R.id.signup_sex);
        signup_age =  findViewById(R.id.signup_age);

        register = findViewById(R.id.register);
        //if button clicked
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=signup_username.getText().toString().trim();
                password=signup_password.getText().toString().trim();
                confirmpassword=signup_confirmpassword.getText().toString().trim();
                firstname = signup_firtname.getText().toString().toUpperCase();
                middlename = signup_middlename.getText().toString().toUpperCase();
                lastname = signup_lastname.getText().toString().toUpperCase();
                address = signup_address.getText().toString().toUpperCase();
                contactnumber = signup_contactnumber.getText().toString();
                sex = signup_sex.getText().toString().toUpperCase();
                age = signup_age.getText().toString();

                if (username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || firstname.isEmpty() || middlename.isEmpty()
                        || lastname.isEmpty()|| address.isEmpty()|| contactnumber.isEmpty()|| sex.isEmpty()|| age.isEmpty()){
                    //if all fields is empty
                    Toast.makeText(Register.this, "Check Empty Fields...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register.this, "Please Try Again...", Toast.LENGTH_SHORT).show();

                }else{
                    if (password.equals(confirmpassword)){
                        //your code here to pass the value
                        username=signup_username.getText().toString().trim();
                        password=signup_password.getText().toString().trim();
                        confirmpassword=signup_confirmpassword.getText().toString().trim();
                        firstname = signup_firtname.getText().toString().toUpperCase();
                        middlename = signup_middlename.getText().toString().toUpperCase();
                        lastname = signup_lastname.getText().toString().toUpperCase();
                        address = signup_address.getText().toString().toUpperCase();
                        contactnumber = signup_contactnumber.getText().toString();
                        sex = signup_sex.getText().toString().toUpperCase();
                        age = signup_age.getText().toString();

                        UserRegisterFunction(username,password,firstname,middlename,lastname,address,contactnumber,sex,age);


                    }else {
                        Toast.makeText(Register.this, "Password Doesn't Match...", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
    public void UserRegisterFunction(final String username, final String password, final String firstname,
                                     final String middlename,final String lastname,final String address,final String contactnumber,
                                     final String sex,final String age){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Register.this, "Signing Up...", "Loading Please Wait");
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("username",params[0]);
                hashMap.put("password",params[1]);
                hashMap.put("firstname",params[2]);
                hashMap.put("middlename",params[3]);
                hashMap.put("lastname",params[4]);
                hashMap.put("address",params[5]);
                hashMap.put("contactnumber",params[6]);
                hashMap.put("sex",params[7]);
                hashMap.put("age",params[8]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(username,password,firstname,middlename,lastname,address,contactnumber,sex,age);
    }


}