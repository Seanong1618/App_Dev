package com.mycompany.sharedpref;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import javax.security.auth.callback.*;

public class MainActivity extends Activity 
{
	EditText loguser,logpass;
	Button button;
	String username="", password="",name="",pass="";
	SharedPreferences sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		loguser = findViewById(R.id.loguser);
		logpass = findViewById(R.id.logpass);
		button = findViewById(R.id.button);
		
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick (View view){
				sharedpref =getSharedPreferences("mypref",MODE_PRIVATE);
				username = loguser.getText().toString().trim();
				password = logpass.getText().toString().trim();
				
				//get values from shared pref
				 name =sharedpref.getString("username",null);
				 pass =sharedpref.getString("password",null);
				
				if (username.equals("") || password.equals("")){
					Toast toast=Toast. makeText(getApplicationContext(),"Empty fields!",Toast. LENGTH_SHORT);
					toast. show();
				}else if (!username.equals(name) || !password.equals(pass)){
					Toast toast=Toast. makeText(getApplicationContext(),"Invalid Credentials!",Toast. LENGTH_SHORT);
					toast. show();
				}else if (name.equals(username) || pass.equals(password)){
					Intent intent =new Intent(getApplicationContext(),success.class);
					startActivity(intent);
				}
				
			}
		});
		
    }
	public void signup(View view){
		//signup
		Intent sean = new Intent (getApplicationContext(), signup.class);
		startActivity(sean);
	}
}
