package com.mycompany.myapp;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

public class jo extends Activity 
{
	EditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jo);
		editPhone= findViewById(R.id.editPhone);


    }
	public void btnCall(View view) {
		int permissionCheck =
			ContextCompat.checkSelfPermission(this, 
											  Manifest.permission.CALL_PHONE);
		if (permissionCheck== 
			PackageManager.PERMISSION_GRANTED){
			String phoneNum= "09637891751";

			Intent callIntent = new 
				Intent(Intent.ACTION_CALL);

			callIntent.setData(Uri.parse("tel:"+phoneNum));

			startActivity(callIntent);
		}
		else {
			ActivityCompat.requestPermissions(this, 
											  new String[]{Manifest.permission.CALL_PHONE}, 
											  0);
		}

	}
}

