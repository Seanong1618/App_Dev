package com.mycompany.myapp;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity 
{
	String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		
    }
	//for Messaging
	public void papa (View view) {
		Intent intent = new Intent (this ,papa.class);
		startActivity(intent);
		
		
		
	}
	public void mama (View view) {
		Intent intent = new Intent (this ,mama.class);
		startActivity(intent);
	
		
	}
	public void kim (View view) {
		Intent intent = new Intent (this ,kim.class);
		startActivity(intent);

	}
	public void jonathan (View view) {
		Intent intent = new Intent (this ,jonathan.class);
		startActivity(intent);
	}
	public void jhuna (View view) {
		Intent intent = new Intent (this ,jhuna.class);
		startActivity(intent);
	}
	//for Calling
	public void pa (View view) {
		Intent intent = new Intent (this, pa.class);
		startActivity(intent);
	}
	public void ma (View view) {
		Intent intent = new Intent (this, ma.class);
		startActivity(intent);
	}
	public void ki (View view) {
		Intent intent = new Intent (this, ki.class);
		startActivity(intent);
	}
	public void jo (View view) {
		Intent intent = new Intent (this, jo.class);
		startActivity(intent);
	}
	public void jh (View view) {
		Intent intent = new Intent (this, jh.class);
		startActivity(intent);
	}
	
}

