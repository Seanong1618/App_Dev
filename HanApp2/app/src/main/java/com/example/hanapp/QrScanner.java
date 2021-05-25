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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.encoder.QRCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class QrScanner extends AppCompatActivity {
    EditText temp;
    String temp_holder = "", Data = "";
    Float temperature;
    String Username_home1 = "" , Password_home1 = "",json_data = "";

    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String HttpURL = "http://hanapp2021.000webhostapp.com/Confirmation.php";

    Button scan_button,submit;
    TextView textView;
    private static final int CAMERA_PERMISSION_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        //action button
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        Username_home1 = sharedPreferences.getString("Username_login","");
        Password_home1 = sharedPreferences.getString("Password_login","");
        temp = findViewById(R.id.temp);
        temp_holder = temp.getText().toString().trim();
        submit = findViewById(R.id.submit);
        textView = findViewById(R.id.data);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the submit button was click
                temp_holder = temp.getText().toString().trim();
                temperature = Float.parseFloat(temp_holder.trim());
                //if the Data from qr code and temp is empty
                if (temp_holder.length() <= 1 || temp_holder.length() >= 5){
                    Toast.makeText(QrScanner.this, "Invalid Temperature Please try again...", Toast.LENGTH_SHORT).show();
                }else if (temperature >= 37.6){
                    Toast.makeText(QrScanner.this, "In compliance with the existing Protocol your temperature is too high...", Toast.LENGTH_LONG).show();
                }else if (temperature <= 35){
                    Toast.makeText(QrScanner.this, "Your Temperature is LOW...", Toast.LENGTH_SHORT).show();
                }else if (temp_holder.equals("") && Data.equals("")){

                } else{
                    temp_holder = temp.getText().toString().trim();
                    //values will pass to the function
                    SharedPreferences sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Username", Username_home1);
                    editor.putString("Password", Password_home1);
                    editor.putString("Data", Data);
                    editor.putString("Temperature", temp_holder);
                    editor.apply();
                    UserRegisterFunction(Data);
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
    public void UserRegisterFunction(final String EstablishmentId){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(QrScanner.this, "", "Loading Please Wait");
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                json_data = httpResponseMsg;
                Intent intent = new Intent(getBaseContext(), Confirmation_QRCode.class);
                intent.putExtra("json_parse", json_data);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("estID",params[0]);



                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(EstablishmentId);
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
                Toast.makeText(this,"No Data...Try Again",Toast.LENGTH_SHORT).show();
            }
            else {
                Data = result.getContents();
                textView.setText(Data);
            }
        }
        else {
            Toast.makeText(this,"No Data...Try Again",Toast.LENGTH_SHORT).show();
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

