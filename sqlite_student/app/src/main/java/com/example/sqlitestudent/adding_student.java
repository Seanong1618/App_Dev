package com.example.sqlitestudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class adding_student extends AppCompatActivity {

    OpenHelper openHelper;
    String stID;
    String stName;
    String stAddress;
    SQLiteDatabase sqLiteDatabase;

    EditText eID;
    EditText eName;
    EditText eAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_student);
        openHelper = new OpenHelper(this);
        sqLiteDatabase = openHelper.getWritableDatabase();

        eID = findViewById(R.id.idno_txt);
        eName = findViewById(R.id.name_txt);
        eAddress = findViewById(R.id.address_txt);
    }
    public void clickAdd(View view) {
        stID=eID.getText().toString();
        stName=eName.getText().toString();
        stAddress=eAddress.getText().toString();

        if(TextUtils.isEmpty(stID) || TextUtils.isEmpty(stName) || TextUtils.isEmpty(stAddress)) {
            Toast.makeText(this, "Check the Empty Fields", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseInfo._ID, (byte[]) null);
            contentValues.put(DatabaseInfo.IDNO, stID);
            contentValues.put(DatabaseInfo.StudentName, stName);
            contentValues.put(DatabaseInfo.StudentAddress, stAddress);
             long rowId = sqLiteDatabase.insert(DatabaseInfo.TABLE_NAME, null, contentValues);

             if (rowId != -1) {
                 Toast.makeText(this, "Added",Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(this, MainActivity.class));
             }
             else {
                 Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
             }
        }
    }
}