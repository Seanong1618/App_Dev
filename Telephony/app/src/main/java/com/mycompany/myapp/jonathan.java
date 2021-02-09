package com.mycompany.myapp;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.support.v4.app.ActivityCompat;



public class  jonathan extends Activity 
{
	EditText editPhone, editMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jonathan);

		editPhone= findViewById(R.id.editPhone);
		editMessage= findViewById(R.id.editMessage);
	}
	public void btnSend(View view) {
		int permissionCheck = 
			ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
		if (permissionCheck== 
			PackageManager.PERMISSION_GRANTED){
			sendMessage();
		}else {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
		}
	}

	private void sendMessage()
	{
		// TODO: Implement this method
		String phone, message;
		phone= editPhone.getText().toString().trim();
		message= editMessage.getText().toString().trim();
		SmsManager smsManager = SmsManager.getDefault();

		smsManager.sendTextMessage(phone, null, 
								   message, null, null);
		Toast.makeText(this, "Message Sent", 
					   Toast.LENGTH_SHORT).show();
	}


}

    
