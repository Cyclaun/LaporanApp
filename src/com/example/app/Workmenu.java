package com.example.app;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class Workmenu extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workmenu);

		((Button) findViewById(R.id.btnPost))
		.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
				Workmenu.this.startActivity(new Intent(
						Workmenu.this, Work.class));
			}
		});
		
		((Button) findViewById(R.id.btnHistory))
		.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
				Workmenu.this.startActivity(new Intent(
						Workmenu.this, History.class));
			}
		});
		
	}

}
