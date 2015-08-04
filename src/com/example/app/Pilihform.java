package com.example.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Pilihform extends Activity {

	SharedPreferences share;
	TextView lblWelcome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pilihform);
		
		lblWelcome = (TextView) findViewById(R.id.lblWelcome);
		share = this.getSharedPreferences("aino", MODE_PRIVATE);
		lblWelcome.setText("Welcome " + share.getString("nama", ""));

		((Button) findViewById(R.id.btnwork))
		.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
				Pilihform.this.startActivity(new Intent(
						Pilihform.this, Work.class));
			}
		});
		
		((Button) findViewById(R.id.btnshare))
		.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
				Pilihform.this.startActivity(new Intent(
						Pilihform.this, Share.class));
			}
		});
		
		((Button) findViewById(R.id.btnlogout))
		.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							finish();
							Pilihform.this.startActivity(new Intent(
									Pilihform.this, MainActivity.class));
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							dialog.cancel();
							break;
						}
					}
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(Pilihform.this);
				builder.setMessage("Tekan Ya untuk melanjutkan Log out ?")
						.setPositiveButton("Ya", dialogClickListener)
						.setNegativeButton("Tidak", dialogClickListener).show();
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
	}

}
