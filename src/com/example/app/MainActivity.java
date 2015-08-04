package com.example.app;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import coding.JSONParser;

public class MainActivity extends Activity implements OnClickListener{

	EditText name;
	EditText pass;
	Button login;
	Button register;
	SharedPreferences share;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		name = (EditText) findViewById(R.id.txtKepentinggan);
		pass = (EditText) findViewById(R.id.txtBiaya);
		login = (Button) findViewById(R.id.button1);
		register = (Button) findViewById(R.id.button2);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		
		share = this.getSharedPreferences("aino", MODE_PRIVATE);
	}

	@Override
	public void onClick(View v) {
		String na = name.getText().toString();
		String pa = pass.getText().toString();

		switch (v.getId()) {
		case R.id.button1:
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("username", na));
			param.add(new BasicNameValuePair("pass", pa));
			JSONObject json = new JSONParser().makeHttpRequest(
					"http://datakaryawan.esy.es/login.php", "POST", param);
			if (!json.optString("nama").equals("")) {
				SharedPreferences.Editor edit = share.edit();
				edit.putString("nama", json.optString("nama"));
				edit.putString("username", json.optString("username"));
				edit.commit();
				finish();
				startActivity(new Intent(MainActivity.this, Pilihform.class));
			} else {
				Toast.makeText(MainActivity.this, "Username atau Password salah !", Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.button2:
			((Button) findViewById(R.id.button2))
			.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					finish();
					MainActivity.this.startActivity(new Intent(
							MainActivity.this, Regristasi.class));
				}
			});
			
			break;
		default:
			break;
		}

	}
	
	@Override
	public void onBackPressed() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					moveTaskToBack(true);
					System.exit(0);
					android.os.Process.killProcess(android.os.Process.myPid());
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					dialog.cancel();
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Apakah Anda ingin keluar dari Aplikasi ?")
				.setPositiveButton("Ya", dialogClickListener)
				.setNegativeButton("Tidak", dialogClickListener).show();
	}

}
