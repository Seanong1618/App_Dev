package com.example.hanapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class Register2 extends AppCompatActivity {
    String EstablishmentId_holder="",EstablishmentName_holder="",EstablishmentAddress_holder="",Phonenumber_holder="",Username_holder="",Password_holder="",Confirmpassword_holder="";
    EditText EstablishmentId,EstablishmentName,EstablishmentAddress,Phonenumber,Username,Password,ConfirmPassword;
    String finalResult ;
    String HttpURL = "http://hanapp2021.000webhostapp.com/Establishment_signup.php";
    ProgressDialog progressDialog;
    Boolean CheckEditText ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    Button Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        //actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Establishment Signup");
        actionBar.setDisplayHomeAsUpEnabled(true);
        //initialize
        EstablishmentId = findViewById(R.id.signup_establishmentID);
        EstablishmentName = findViewById(R.id.signup_establishmentname);
        EstablishmentAddress = findViewById(R.id.signup_estadd);
        Phonenumber =  findViewById(R.id.signup_establishmentnumber);
        Username =  findViewById(R.id.signup_establishmentusername);
        Password =  findViewById(R.id.signup_establishmentpassword);
        ConfirmPassword =  findViewById(R.id.signup_establishmentconfirmpassword);
        Register = findViewById(R.id.register2);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check the variable if empty
                EstablishmentId_holder = EstablishmentId.getText().toString().toUpperCase();
                EstablishmentName_holder = EstablishmentName.getText().toString().toUpperCase();
                EstablishmentAddress_holder = EstablishmentAddress.getText().toString().toUpperCase();
                Phonenumber_holder = Phonenumber.getText().toString();
                Username_holder = Username.getText().toString();
                Password_holder = Password.getText().toString();
                Confirmpassword_holder = ConfirmPassword.getText().toString();
                if (EstablishmentId_holder.isEmpty() || EstablishmentName_holder.isEmpty() || EstablishmentAddress_holder.isEmpty() || Phonenumber_holder.isEmpty() || Username_holder.isEmpty() || Password_holder.isEmpty() || Confirmpassword_holder.isEmpty()){
                    Toast.makeText(Register2.this, "Check Empty Fields...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register2.this, "Please Try Again...", Toast.LENGTH_SHORT).show();
                }else {
                    if (Password_holder.equals(Confirmpassword_holder)){

                        EstablishmentId_holder = EstablishmentId.getText().toString().toUpperCase();
                        EstablishmentName_holder = EstablishmentName.getText().toString().toUpperCase();
                        EstablishmentAddress_holder = EstablishmentAddress.getText().toString().toUpperCase();
                        Phonenumber_holder = Phonenumber.getText().toString().trim();
                        Username_holder = Username.getText().toString().trim();
                        Password_holder = Password.getText().toString().trim();

                        UserRegisterFunction(EstablishmentId_holder,EstablishmentName_holder,EstablishmentAddress_holder,
                                Phonenumber_holder,Username_holder,Password_holder);
                    }else {
                        Toast.makeText(Register2.this, "Password Doesn't Match...", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



    }

    public void UserRegisterFunction(final String EstablishmentId_holder, final String EstablishmentName_holder, final String EstablishmentAddress_holder,
                                     final String Phonenumber_holder,final String Username_holder,final String Password_holder){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Register2.this, "Signing Up...", "Loading Please Wait");

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                //wala pa

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("EstablishmentId",params[0]);
                hashMap.put("EstablishmentName",params[1]);
                hashMap.put("EstablishmentAddress",params[2]);
                hashMap.put("Phonenumber",params[3]);
                hashMap.put("Username",params[4]);
                hashMap.put("Password",params[5]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(EstablishmentId_holder,EstablishmentName_holder,EstablishmentAddress_holder,Phonenumber_holder,Username_holder,Password_holder);
    }

}