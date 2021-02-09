package com.example.hanapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

public class QrScanner extends AppCompatActivity {
    EditText temp;
    String temp_holder = "", Data = "";
    String Username_home1 = "" , Password_home1 = "";

    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String HttpURL = "http://hanapp2021.000webhostapp.com/Entry_details.php";
    Button scan_button,submit;
    TextView textView;
    private static final int CAMERA_PERMISSION_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        //action button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Scan QR Code");
        actionBar.setDisplayHomeAsUpEnabled(true);
        //from home1 class
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        Username_home1 = sharedPreferences.getString("Username_home1","");
        Password_home1 = sharedPreferences.getString("Password_home1","");

        temp = findViewById(R.id.temp);
        temp_holder = temp.getText().toString().trim();
        textView = findViewById(R.id.data_text);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the submit button was click
                temp_holder = temp.getText().toString().trim();
                //if the Data from qr code and temp is empty
                if (temp_holder.isEmpty() || Data.isEmpty()){
                    Toast.makeText(QrScanner.this, "Please Check Empty Fields...", Toast.LENGTH_SHORT).show();
                }else{
                    temp_holder =temp.getText().toString().trim();
                    //values will pass to the function
                    UserRegisterFunction(temp_holder,Data,Username_home1,Password_home1);
                }

            }
        });
        scan_button = findViewById(R.id.button_scan);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=23){
                    if (checkPermision(Manifest.permission.CAMERA)){
                        openScanner();
                    }
                    else {
                        requestPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
                    }
                }
                else {
                    openScanner();
                }
            }
        });
    }
    public void UserRegisterFunction(final String temperature_holder, final String Data, final String Username_home1, final String Password_home1){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(QrScanner.this, "Signing Up...", "Loading Please Wait");
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                if (httpResponseMsg.equalsIgnoreCase("Invalid Establishment ID")){

                    Toast.makeText(QrScanner.this, "Invalid Establishment ID...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(QrScanner.this, "Establishment ID is not Registered...", Toast.LENGTH_SHORT).show();

                }else if (httpResponseMsg.equalsIgnoreCase("Data Insertion Success")){
                    Intent intent = new Intent(getApplicationContext(), home1.class);
                    startActivity(intent);
                    Toast.makeText(QrScanner.this, "Successful Insertion...", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QrScanner.this, "Data Insertion Error...", Toast.LENGTH_SHORT).show();
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

    public void openScanner(){
        new IntentIntegrator(QrScanner.this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(this,"Blank",Toast.LENGTH_SHORT).show();
            }
            else {
                Data = result.getContents();
                textView.setText("Data :"+result.getContents());
            }
        }
        else {
            Toast.makeText(this,"Blank",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermision (String permission){
        int result = ContextCompat.checkSelfPermission(QrScanner.this,permission);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    private void requestPermission (String permission, int code){
        if (ActivityCompat.shouldShowRequestPermissionRationale(QrScanner.this,permission)){

        }
        else{
            ActivityCompat.requestPermissions(QrScanner.this, new String[]{permission},code);
        }
    }
    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permission, @NonNull int[] grantResuts){
        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if (grantResuts.length>0 && grantResuts[0] == PackageManager.PERMISSION_GRANTED){
                    openScanner();
                }
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}

