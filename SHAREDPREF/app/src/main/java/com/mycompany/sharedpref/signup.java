package com.mycompany.sharedpref;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import org.apache.http.*;

public class signup extends Activity 
{
	EditText username,password,confirm;
	Button signupButton;
	SharedPreferences sharedpref;
	SharedPreferences.Editor edit;
	String name="",pass="",confirpass="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
		username=findViewById(R.id.signupUsername);
		password=findViewById(R.id.signupPassword);
		confirm=findViewById(R.id.signupConfirm);
		signupButton=findViewById(R.id.signupButton);
		
		signupButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				name=username.getText().toString().trim();
				pass=password.getText().toString().trim();
				confirpass=confirm.getText().toString().trim();
				if(name.equals("")){
					Toast toast=Toast. makeText(getApplicationContext(),"Username is Empty!",Toast. LENGTH_SHORT);
					toast. show();
				}else if(pass.equals("") && confirpass.equals("")){
					Toast toast=Toast. makeText(getApplicationContext(),"Empty Password!",Toast. LENGTH_SHORT);
					toast. show();
				}else if(!pass.equals(confirpass)){
					Toast toast=Toast. makeText(getApplicationContext(),"PASSWORD NOT MATCH!",Toast. LENGTH_SHORT);
					toast. show();
				}else{
					sharedpref=getSharedPreferences("mypref",MODE_PRIVATE);
					edit=sharedpref.edit();
					edit.putString("username",name);
					edit.putString("password",pass);
					edit.commit();
					
					Toast toast=Toast. makeText(getApplicationContext(),"SUCCESSFULL!",Toast. LENGTH_SHORT);
					toast. show();
					
					Intent intent= new Intent (getApplicationContext(), MainActivity.class);
					startActivity(intent);
					
				}
			}
		});

		
		
    }
	
		


}
