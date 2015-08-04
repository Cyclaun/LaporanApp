package com.example.app;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import coding.JSONParser;

public class Regristasi extends Activity {

	EditText txtNama, txtUsername, txtPassword;
	Button btnOk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regristasi);
		
		txtNama = (EditText) findViewById(R.id.txtNama);
		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		btnOk = (Button) findViewById(R.id.btnOk);

	




	btnOk.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if (txtNama.getText().toString().length() == 0) {
				txtNama.setError("Nama harus diisi !");
				txtNama.requestFocus();
			} else if (txtUsername.getText().toString().length() == 0) {
				txtUsername.setError("Username harus diisi !");
				txtNama.requestFocus();
			} else if (txtPassword.getText().toString().length() == 0) {
				txtPassword.setError("Password harus diisi !");
				txtNama.requestFocus();
			} else {
				ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("username", txtUsername.getText().toString()));
				param.add(new BasicNameValuePair("nama", txtNama.getText().toString()));
				param.add(new BasicNameValuePair("pass", txtPassword.getText().toString()));
				JSONObject json = new JSONParser().makeHttpRequest(
						"http://datakaryawan.esy.es/tambahUser.php", "POST", param);
				if (json.optString("sukses").equals("1")) {
					Toast.makeText(Regristasi.this, "Sukses", Toast.LENGTH_LONG).show();
					finish();
					startActivity(new Intent(Regristasi.this, MainActivity.class));
				} else {
					Toast.makeText(Regristasi.this, "Registrasi gagal, username telah dipakai !", Toast.LENGTH_LONG).show();
				}
			}
		}
	});
	
}
	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(Regristasi.this, MainActivity.class));
	}
}
