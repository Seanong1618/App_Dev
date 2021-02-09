package com.mycompany.sharedpref;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;

public class success extends Activity 
{

	TextView username;
	SharedPreferences sharedpref;
	String name;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
		username=findViewById(R.id.successname);
		sharedpref=getSharedPreferences("mypref",MODE_PRIVATE);
		name=sharedpref.getString("username",null);
		username.setText(name);
    }
}
